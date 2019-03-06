package com.guiji.dispatch.pushcallcenter;

import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.enums.GateWayLineStatusEnum;
import com.guiji.dispatch.enums.PlanLineTypeEnum;
import com.guiji.dispatch.util.DateTimeUtils;
import com.guiji.dispatch.vo.GateWayLineOccupyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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

	private static final String REDIS_CALL_QUEUE_USER_LINE_ROBOT_COUNT = "REDIS_CALL_QUEUE_USER_LINE_ROBOT_COUNT";

	@RabbitHandler
	public void process(String message, Channel channel, Message message2) {
		// 呼叫中心回调之后去获取最新的并发数量和呼叫中心的负载情况推送对应数量的号码
		MQSuccPhoneDto mqSuccPhoneDto = JsonUtils.json2Bean(message, MQSuccPhoneDto.class);
		logger.info("呼叫中心回调数据:{}", JsonUtils.bean2Json(mqSuccPhoneDto));
		/********判断处理网关SIM卡线路占用释放	begin*************************************/
		this.leisureGateWayLine(mqSuccPhoneDto);
		/********判断处理网关SIM卡线路占用释放	begin*************************************/
		PushRecordsExample ex = new PushRecordsExample();
		ex.createCriteria().andPlanuuidEqualTo(mqSuccPhoneDto.getPlanuuid())
				.andCallbackStatusEqualTo(Constant.NOCALLBACK);
		PushRecords re = new PushRecords();
		// 设置已经回调的状态
		re.setCallbackStatus(Constant.CALLBACKED);
		int result = recordMapper.updateByExampleSelective(re, ex);
		if (result > 0) {
			String queueCount = REDIS_CALL_QUEUE_USER_LINE_ROBOT_COUNT+mqSuccPhoneDto.getUserId()+"_"+mqSuccPhoneDto.getTempId();
			Integer currentCount = (Integer) redisUtil.get(queueCount);
			if (currentCount > 0) {
//				currentCount = currentCount - 1;
//				redisUtil.set(queueCount, currentCount);
				redisUtil.decr(queueCount, 1);
				redisUtil.expire(queueCount, 300);
			}


		}
	}

	/**
	 * 处理网关线路释放
	 * @param mqSuccPhoneDto
	 */
	private void leisureGateWayLine(MQSuccPhoneDto mqSuccPhoneDto){
		try {
			if (null != mqSuccPhoneDto) {
				String userId = mqSuccPhoneDto.getUserId()+"";
				String botstenceId = mqSuccPhoneDto.getTempId();
				//获取网关线路对象
				Integer lineId = mqSuccPhoneDto.getLineId();
				GateWayLineOccupyVo gateWayLine = (GateWayLineOccupyVo) redisUtil.get(RedisConstant.RedisConstantKey.gatewayLineKey + lineId);
				if (null != gateWayLine) {
					//获取用户线路机器人分配
					String key = RedisConstant.RedisConstantKey.REDIS_PLAN_QUEUE_USER_LINE_ROBOT + mqSuccPhoneDto.getUserId() + "_" + mqSuccPhoneDto.getTempId();
					Object obj = (Object) redisUtil.get(key);
					if (null != obj) {
						DispatchPlan dispatchRedis = (DispatchPlan) obj;
						//判断是否是网关线路
						if (null != dispatchRedis && PlanLineTypeEnum.GATEWAY.getType() == dispatchRedis.getLineType()) {
							//释放网关路线
							this.releaseGateWay(gateWayLine, lineId);
						}
					} else {
						if (gateWayLine.getUserId().equals(userId)
								&& gateWayLine.getBotstenceId().equals(botstenceId)) {
							//释放网关路线
							this.releaseGateWay(gateWayLine, lineId);
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
	 * @param lineId
	 */
	private void releaseGateWay(GateWayLineOccupyVo gateWayLine, Integer lineId){
		//释放网关线路状态
		gateWayLine.setStatus(GateWayLineStatusEnum.LEISURE.getState());
		gateWayLine.setReleaseTime(DateTimeUtils.getDateString(new Date(),DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL));
	//	gateWayLine.setUserId(null);
	//	gateWayLine.setBotstenceId(null);
		redisUtil.set(RedisConstant.RedisConstantKey.gatewayLineKey + lineId,
				gateWayLine);
	}
}
