package com.guiji.dispatch.pushcallcenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.vo.TotalPlanCountVo;
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
import com.guiji.dispatch.mq.ModularMqListener;
import com.guiji.dispatch.thirdinterface.SuccPhonesThirdInterface;
import com.guiji.dispatch.util.Constant;
import com.guiji.notice.api.INoticeSend;
import com.guiji.notice.enm.NoticeType;
import com.guiji.notice.entity.MessageSend;
import com.guiji.sms.api.ISms;
import com.guiji.sms.vo.SendMReqVO;
import com.guiji.utils.DateUtil;
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
@RabbitListener(queues = "dispatch.SuccessPhoneMQ")
public class SuccesPhone4MQLisener {
	private static Logger logger = LoggerFactory.getLogger(ModularMqListener.class);

	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;

	@Autowired
	private SuccPhonesThirdInterface thirdInterface;

	@Autowired
	private IDispatchPlanService dispatchPlanService;

	@Autowired
	private ISms sms;

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
			MQSuccPhoneDto mqSuccPhoneDto = JsonUtils.json2Bean(message, MQSuccPhoneDto.class);
			logger.info("当前队列任务接受的uuid：" + mqSuccPhoneDto.getPlanuuid());
			DispatchPlanExample ex = new DispatchPlanExample();
			ex.createCriteria().andPlanUuidEqualTo(mqSuccPhoneDto.getPlanuuid());
			List<DispatchPlan> list = dispatchPlanMapper.selectByExample(ex);
			if (list.size() <= 0) {
				logger.info("当前队列任务回调 uuid错误！");
				return;
			} else {
				DispatchPlan dispatchPlan = list.get(0);
				dispatchPlan.setStatusPlan(Constant.STATUSPLAN_2);// 2计划完成
				// 增加意向标签
				dispatchPlan.setResult(mqSuccPhoneDto.getLabel());
				int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex);
				logger.info("当前队列任务回调修改结果" + result);
				// 查询当前是否批次结束
				MessageSend send = selectBatchOver(dispatchPlan);
				if (send != null) {
					logger.info("当前批次结束,通知结束消息：" + dispatchPlan.getBatchId());
					sendMsg.sendMessage(send);
				}
				// 第三方回调
				thirdInterface.execute(dispatchPlan);
				// 发送短信
				SendMReqVO vo = new SendMReqVO();
				vo.setOrgCode(dispatchPlan.getOrgCode());
				vo.setPhone(dispatchPlan.getPhone());
				vo.setUserId(dispatchPlan.getUserId());
				vo.setIntentionTag(mqSuccPhoneDto.getLabel());
				vo.setTemplateId(dispatchPlan.getRobot());
				sms.sendMessage(vo);
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
	 * 消息通知
	 * @param dispatchPlan
	 * @return
	 */
	private MessageSend selectBatchOver(DispatchPlan dispatchPlan) {
		/*
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andBatchIdEqualTo(dispatchPlan.getBatchId()).andStatusPlanEqualTo(Constant.STATUSPLAN_1)
				.andIsDelEqualTo(Constant.IS_DEL_0).andUserIdEqualTo(dispatchPlan.getUserId());
		int count = dispatchPlanMapper.countByExample(ex);
		*/

		/*DispatchPlanExample ex1 = new DispatchPlanExample();
		ex1.createCriteria().andBatchIdEqualTo(dispatchPlan.getBatchId());
		int batchCount = dispatchPlanMapper.countByExample(ex1);*/

		//统计计划数量(已完成，计划中，暂停中，停止中)
		TotalPlanCountVo totalCount = dispatchPlanService.totalPlanCountByBatch(dispatchPlan.getBatchId());
	//	if (count == 0) {
		if (totalCount.getFinishCount()>0 && totalCount.getDoingCount() == 0
				&& totalCount.getStopCount()==0 && totalCount.getSuspendCount()==0) {
			MessageSend send = new MessageSend();
			send.setUserId(dispatchPlan.getUserId().longValue());
			send.setNoticeType(NoticeType.task_finish);
			send.setSmsContent("您在" + DateUtil.formatDatetime(dispatchPlan.getGmtCreate()) + "创建的" + totalCount.getFinishCount()	//batchCount
					+ "通号码的外呼任务已完成，请登录系统查看外呼结果");
			send.setMailContent("您在" + DateUtil.formatDatetime(dispatchPlan.getGmtCreate()) + "创建的" + totalCount.getFinishCount()	//batchCount
					+ "通号码的外呼任务已完成，点击查看外呼结果");
			send.setEmailContent("您在" + DateUtil.formatDatetime(dispatchPlan.getGmtCreate()) + "创建的" + totalCount.getFinishCount()	//batchCount
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
		return null;
	}

}
