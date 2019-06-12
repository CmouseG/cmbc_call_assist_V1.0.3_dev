package com.guiji.dispatch.pushcallcenter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.enums.IsNotifyMsgEnum;
import com.guiji.dispatch.enums.PlanStatusEnum;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.util.DateTimeUtils;
import com.guiji.dispatch.vo.TotalPlanCountVo;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.sms.api.bean.SendMReqVO;
import com.guiji.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.thirdinterface.SuccPhonesThirdInterface;
import com.guiji.dispatch.util.Constant;
import com.guiji.notice.api.INoticeSend;
import com.guiji.notice.enm.NoticeType;
import com.guiji.notice.entity.MessageSend;
import com.guiji.utils.DateUtil;
import com.guiji.utils.IdGengerator.IdUtils;
import com.guiji.utils.JsonUtils;
import com.guiji.wechat.vo.SendMsgReqVO;
import com.rabbitmq.client.Channel;

/**
 * 任务回调之后做处理
 * 
 * @author Administrator
 *
 */
@Component
@RabbitListener(queues = "dispatch.SuccessPhoneMQ", containerFactory = "successMqRabbitFactory")
public class SuccesPhone4MQLisener {
	private static Logger logger = LoggerFactory.getLogger(SuccesPhone4MQLisener.class);

	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;

	@Autowired
	private SuccPhonesThirdInterface thirdInterface;

	@Autowired
	private IDispatchPlanService dispatchPlanService;

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	QueueSender queueSender;

	@Autowired
	private INoticeSend sendMsg;

	@Value("${weixin.templateId}")
	String weixinTemplateId;
	@Value("${weixin.appid}")
	String weixinAppid;
	@Value("${weixin.pagePath.mainUrl}")
	String mainUrl;

	@Async("asyncSuccPhoneExecutor")
	@RabbitHandler
	public void process(String message, Channel channel, Message message2) {
		try {
			if(null == message || "".equals(message)){
				return;
			}
			MQSuccPhoneDto mqSuccPhoneDto = JsonUtils.json2Bean(message, MQSuccPhoneDto.class);
			String time = DateTimeUtils.getCurrentDateString(DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
			logger.info("当前队列任务接受的回调：" + message);
			logger.warn("呼叫中心回调数据，号码:{}, 时间:{}, 模块:{}, 操作:{}, 内容:{}", mqSuccPhoneDto.getPlanuuid(), time,
					"guiyu_dispatch", "呼叫中心回调SuccesPhone4MQLisener.process", message);
			//判断线路是否可用
			if (null != mqSuccPhoneDto
					&& null != mqSuccPhoneDto.getSimLineIsOk() && !mqSuccPhoneDto.getSimLineIsOk()) {
			    logger.info("当前计划plan_uuid:{},线路id:{}不可用", mqSuccPhoneDto.getPlanuuid(), mqSuccPhoneDto.getLineId());
				return;
			}

			//判断线路是否被限制
			if (null != mqSuccPhoneDto
					&& null != mqSuccPhoneDto.getSimLimitFlag() && !mqSuccPhoneDto.getSimLimitFlag()) {
				logger.info("当前计划plan_uuid:{},线路id:{}设置拨打限制", mqSuccPhoneDto.getPlanuuid(), mqSuccPhoneDto.getLineId());
				return;
			}

			Long planUuid = Long.valueOf(mqSuccPhoneDto.getPlanuuid());
			Integer orgId = IdUtils.doParse(Long.valueOf(mqSuccPhoneDto.getPlanuuid())).getOrgId();
			DispatchPlan dispatchPlan = dispatchPlanMapper.queryDispatchPlanById(planUuid, orgId);
			if (null == dispatchPlan) {
				logger.info("当前队列任务回调 uuid错误！");
				return;
			} else {
				String result =  mqSuccPhoneDto.getLabel();
				dispatchPlanMapper.finishPlanById(planUuid, PlanStatusEnum.FINISH.getStatus(), result,
						new Date(), orgId);
				logger.info("当前队列任务回调修改结果" + result);
				//消息通知(改成定时任务，每小时执行一次)
			//	this.sendMsgNotify(dispatchPlan);
				// 第三方回调
				thirdInterface.execute(dispatchPlan);
				// 发送短信
				this.sendSms(dispatchPlan);
			}
		} catch (Exception e) {
			logger.info("SuccesPhone4MQLisener消费数据有问题" + message);
			try {
				channel.basicAck(message2.getMessageProperties().getDeliveryTag(), false);
			} catch (IOException e1) {
				logger.info("SuccesPhone4MQLisener ack确认机制有问题");
			}
		}
	}

	/**
	 * 发送短信
	 * @param dispatchPlan
	 */
	@Async
	protected void sendSms(DispatchPlan dispatchPlan){
		try{
			SendMReqVO vo = new SendMReqVO();
			vo.setOrgCode(dispatchPlan.getOrgCode());
			vo.setPhone(dispatchPlan.getPhone());
			vo.setUserId(Long.valueOf(dispatchPlan.getUserId()));
			vo.setIntentionTag(dispatchPlan.getResult());
			vo.setTemplateId(dispatchPlan.getRobot());
			queueSender.send("SendMessageMQ.direct.Sms", JsonUtils.bean2Json(vo));
		}catch(Exception e){
			logger.error("发送短信失败", e);
		}
	}

	/**
	 * 发送消息通知
	 * @param dispatchPlan
	 */
	@Async
	protected void sendMsgNotify(DispatchPlan dispatchPlan){
		// 查询当前是否批次结束
		MessageSend send = selectBatchOver(dispatchPlan);
		String redisKey = RedisConstant.RedisConstantKey.MSG_NOTIFY_FLAG_ + dispatchPlan.getBatchId();
		Object obj = redisUtil.get(redisKey);
		if (send != null && null == obj){
				//消息推送标识不存在，或未推送
		//		&& (null == obj || (null != obj && !IsNotifyMsgEnum.HAVING.getFlag().equals((String)obj)))) {
			logger.info("当前批次结束,通知结束消息：" + dispatchPlan.getBatchId());
			sendMsg.sendMessage(send);
			redisUtil.set(redisKey, IsNotifyMsgEnum.HAVING.getFlag());
			redisUtil.expire(redisKey, 60);//失效时间
		}
	}
	/**
	 * 消息通知
	 * @param dispatchPlan
	 * @return
	 */
	private MessageSend selectBatchOver(DispatchPlan dispatchPlan) {
		try {
			String batchName = dispatchPlan.getBatchName();
			DispatchPlanExample ex = new DispatchPlanExample();
			ex.createCriteria().andBatchIdEqualTo(dispatchPlan.getBatchId())
					.andStatusPlanEqualTo(Constant.STATUSPLAN_1)
					.andUserIdEqualTo(dispatchPlan.getUserId())
					.andOrgIdEqualTo(dispatchPlan.getOrgId());
			int count = dispatchPlanMapper.countByExample(ex);

			//统计计划数量(已完成，计划中，暂停中，停止中)
			//	TotalPlanCountVo totalCount = dispatchPlanService.totalPlanCountByBatch(dispatchPlan.getBatchId(), dispatchPlan.getOrgId());
			if (count == 0) {
				//	if (totalCount.getFinishCount()>0 && totalCount.getDoingCount() == 0) {
				MessageSend send = new MessageSend();
				send.setUserId(dispatchPlan.getUserId().longValue());
				send.setNoticeType(NoticeType.task_finish);
				send.setSmsContent("您在" + DateUtil.formatDatetime(dispatchPlan.getGmtCreate()) + "创建的" + batchName //totalCount.getFinishCount()	//batchCount
						+ "通号码的外呼任务已完成，请登录系统查看外呼结果");
				send.setMailContent("您在" + DateUtil.formatDatetime(dispatchPlan.getGmtCreate()) + "创建的" + batchName    //totalCount.getFinishCount()	//batchCount
						+ "通号码的外呼任务已完成，点击查看外呼结果");
				send.setEmailContent("您在" + DateUtil.formatDatetime(dispatchPlan.getGmtCreate()) + "创建的" + batchName    //totalCount.getFinishCount()	//batchCount
						+ "通号码的外呼任务已完成，请登录系统查看外呼结果");
				send.setEmailSubject("任务完成");
				// 微信
				send.setWeixinTemplateId(weixinTemplateId);
				send.setWeixinPagePath(mainUrl);
				send.setWeixinAppId(weixinAppid);
				HashMap<String, SendMsgReqVO.Item> map = new HashMap<>();
				//	map.put("keyword1", new SendMsgReqVO.Item(dispatchPlan.getUserId(), null));
				map.put("keyword2", new SendMsgReqVO.Item("任务完成", null));
				map.put("keyword3", new SendMsgReqVO.Item("您的外呼任务已完成哦！请登录系统查看外呼结果", null));
				send.setWeixinData(map);
				return send;
			}
		}catch(Exception e){
			logger.error("消息通知异常", e);
		}
		return null;
	}

}
