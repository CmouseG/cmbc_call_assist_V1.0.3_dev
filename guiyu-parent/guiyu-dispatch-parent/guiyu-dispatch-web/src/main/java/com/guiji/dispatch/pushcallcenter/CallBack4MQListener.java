package com.guiji.dispatch.pushcallcenter;

import com.alibaba.druid.support.json.JSONUtils;
import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.enums.GateWayLineStatusEnum;
import com.guiji.dispatch.enums.PlanLineTypeEnum;
import com.guiji.dispatch.model.MqNotifyMessage;
import com.guiji.dispatch.model.PlanCallResultVo;
import com.guiji.dispatch.service.GetApiService;
import com.guiji.dispatch.service.ThirdApiNotifyService;
import com.guiji.dispatch.util.DateTimeUtils;
import com.guiji.dispatch.vo.GateWayLineOccupyVo;
import com.guiji.guiyu.message.component.QueueSender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.dispatch.dao.PushRecordsMapper;
import com.guiji.dispatch.dao.entity.PushRecords;
import com.guiji.dispatch.dao.entity.PushRecordsExample;
import com.guiji.dispatch.util.Constant;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import com.rabbitmq.client.Channel;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 呼叫中心回调记录
 * 
 * @author Administrator
 *
 */
@Component
@RabbitListener(queues = "dispatch.CallBackEvent")
public class CallBack4MQListener {

	private Logger logger = LoggerFactory.getLogger(CallBack4MQListener.class);

	@Autowired
	private PushRecordsMapper recordMapper;
	@Autowired
	private RedisUtil redisUtil;

	@RabbitHandler
	public void process(String message, Channel channel, Message message2) {
		if(null == message || "".equals(message)){
			return;
		}
		// 呼叫中心回调之后去获取最新的并发数量和呼叫中心的负载情况推送对应数量的号码
		MQSuccPhoneDto mqSuccPhoneDto = JsonUtils.json2Bean(message, MQSuccPhoneDto.class);
		String time = DateTimeUtils.getCurrentDateString(DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
		logger.info("呼叫中心回调数据:{},回调时间:{}", message, time);
		logger.warn("呼叫中心回调数据，号码:{}, 时间:{}, 模块:{}, 操作:{}, 内容:{}", mqSuccPhoneDto.getPlanuuid(), time,
				"guiyu_dispatch", "呼叫中心回调CallBack4MQListener.process", message);
		/********判断处理网关SIM卡线路占用释放	begin*************************************/
		this.leisureGateWayLine(mqSuccPhoneDto);
		/********判断处理网关SIM卡线路是否可用,不可用则重新推入队列	begin*****************/
		//判断线路是否可用
		if (null != mqSuccPhoneDto
				&& null != mqSuccPhoneDto.getSimLineIsOk() && !mqSuccPhoneDto.getSimLineIsOk()) {
			this.checkSimLineDisabled(mqSuccPhoneDto);//线路不可用，则放回队列
			this.counterDecr(mqSuccPhoneDto.getUserId(), mqSuccPhoneDto.getTempId());//计数器-1
		}else {
			PushRecordsExample ex = new PushRecordsExample();
			ex.createCriteria().andPlanuuidEqualTo(mqSuccPhoneDto.getPlanuuid())
					.andCallbackStatusEqualTo(Constant.NOCALLBACK);
			PushRecords re = new PushRecords();
			// 设置已经回调的状态
			re.setCallbackStatus(Constant.CALLBACKED);
			int result = recordMapper.updateByExampleSelective(re, ex);
			if (result > 0) {
				this.counterDecr(mqSuccPhoneDto.getUserId(), mqSuccPhoneDto.getTempId());//计数器-1
			}
		}

		logger.info("ready to notify third api: {}", JsonUtils.bean2Json(mqSuccPhoneDto));

		//通知三方单个号码完成
		this.notifyThirdResult(mqSuccPhoneDto.getUserId(), mqSuccPhoneDto.getPlanuuid(), mqSuccPhoneDto.getLabel());
	}

	@Autowired
	GetApiService getApiService;

	@Autowired
	DispatchPlanMapper planMapper;

	@Autowired
	ThirdApiNotifyService thirdApiNotifyService;

	private static final String NOTIFY_QUEUE = "thirdApi.notify";

	/**
	 * 通知三方单个号码完成
	 * @param planUuid
	 * @param result
	 */
	@Async("asyncSuccPhoneExecutor")
	protected void notifyThirdResult(Integer userId, String planUuid, String result){
		Integer orgId = getApiService.getOrgIdByUser(userId + "");
		DispatchPlan vo = planMapper.queryDispatchPlanById(Long.valueOf(planUuid), orgId);

		logger.info("notify plan info: {}", JsonUtils.bean2Json(vo));

        try {
            thirdApiNotifyService.singleNotify(vo);
        }catch(Exception e){
            logger.error("MQ通知三方完成异常", e);
        }
	}


	/**
	 * 计数器减1
	 * @param userId
	 * @param tempId
	 */
	private void counterDecr(Integer userId, String tempId){
		try {
			String queueCount = RedisConstant.RedisConstantKey.REDIS_CALL_QUEUE_USER_LINE_ROBOT_COUNT + userId + "_" + tempId;
			Object countObj = redisUtil.get(queueCount);
			Integer currentCount = null != countObj ? ((Integer) countObj) : null;
			if (null != currentCount && currentCount > 0) {
				redisUtil.decr(queueCount, 1);
				redisUtil.expire(queueCount, 900);
			} else {
				logger.error("呼叫回调，" + queueCount + "已不存在");
			}
		} catch (Exception e) {
			logger.error("计算器异常", e);
		}
	}

	/**
	 * 判断处理网关SIM卡线路是否可用,不可用则重新推入队列
	 * @param mqSuccPhoneDto
	 */
	private void checkSimLineDisabled(MQSuccPhoneDto mqSuccPhoneDto){
		try {
			//判断SIM卡线路是否可用
			if (null != mqSuccPhoneDto
					&& null != mqSuccPhoneDto.getSimLineIsOk() && !mqSuccPhoneDto.getSimLineIsOk()) {
				//从redis中获取之前从队列获取的任务数据
				String planUuid = mqSuccPhoneDto.getPlanuuid();
				String lineDisabledKey = RedisConstant.RedisConstantKey.LINE_DISABLED + planUuid;
				//队列
				Integer userId = mqSuccPhoneDto.getUserId();
				String tempId = mqSuccPhoneDto.getTempId();
				String planQueue = RedisConstant.RedisConstantKey.REDIS_PLAN_QUEUE_USER_LINE_ROBOT + userId + "_" + tempId;
				Object obj = redisUtil.get(lineDisabledKey);
				if (null != obj) {
					DispatchPlan dispatchRedis = (DispatchPlan) obj;
					//不可用，重新推入队列
					redisUtil.leftPush(planQueue, dispatchRedis);
					redisUtil.del(lineDisabledKey);
				}
			}
		}catch(Exception e){
			logger.error("判断SIM卡线路是否可用,重新推入推列异常", e);
		}
	}

	/**
	 * 处理网关线路释放
	 * @param mqSuccPhoneDto
	 */
	private void leisureGateWayLine(MQSuccPhoneDto mqSuccPhoneDto){
		try {
			if (null != mqSuccPhoneDto) {
				//获取网关线路对象
				Integer lineId = mqSuccPhoneDto.getLineId();
			//	logger.info("释放网关线路ID:{}", lineId);
				if(null != lineId) {

                    if(redisUtil.get(RedisConstant.RedisConstantKey.gatewayLineKeyTmp+lineId) != null)
                    {
                        redisUtil.set(RedisConstant.RedisConstantKey.gatewayLineKeyTmp+lineId, 0, 10);
                    }

					String gateWayLineKey = RedisConstant.RedisConstantKey.gatewayLineKey + lineId;
					GateWayLineOccupyVo gateWayLine = (GateWayLineOccupyVo) redisUtil.get(gateWayLineKey);
			//		logger.info("释放网关线路ID:{},对象:{}", lineId, JsonUtils.bean2Json(gateWayLine));
					if (null != gateWayLine) {
							//释放网关路线
							this.releaseGateWay(gateWayLine, gateWayLineKey);
					}

				}else{
					//如果返回lineId，则模糊匹配查询出所有网关路线
					Set<String> gatewayLineKeySet = redisUtil.getAllKeyMatch(RedisConstant.RedisConstantKey.gatewayLineKey);
					if(null != gatewayLineKeySet){
						Iterator<String> iter =gatewayLineKeySet.iterator();
						while(iter.hasNext()){
							//获取网关线路
							String gateWayLineKey = iter.next();
							GateWayLineOccupyVo gateWayLine = (GateWayLineOccupyVo)redisUtil.get(gateWayLineKey);
								//释放网关路线
								this.releaseGateWay(gateWayLine, gateWayLineKey);
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("处理网关线路占用释放异常:" + ((null != mqSuccPhoneDto)?JsonUtils.bean2Json(mqSuccPhoneDto):null), e);
		}

	}

	/**
	 * 释放网关路线占用
	 * @param gateWayLine
	 * @param gateWayLineKey
	 */
	private void releaseGateWay(GateWayLineOccupyVo gateWayLine, String gateWayLineKey){
		//释放网关线路状态
		gateWayLine.setStatus(GateWayLineStatusEnum.LEISURE.getState());
		gateWayLine.setReleaseTime(DateTimeUtils.getDateString(new Date(),DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL));
		redisUtil.set(gateWayLineKey, gateWayLine);
	}
}
