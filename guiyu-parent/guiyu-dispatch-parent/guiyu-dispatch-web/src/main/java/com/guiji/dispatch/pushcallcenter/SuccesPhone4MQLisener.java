package com.guiji.dispatch.pushcallcenter;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.mq.ModularMqListener;
import com.guiji.dispatch.sms.SendSmsInterface;
import com.guiji.dispatch.thirdinterface.SuccPhonesThirdInterface;
import com.guiji.dispatch.util.Constant;
import com.guiji.utils.JsonUtils;
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
	private SendSmsInterface smsInterface;

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
				int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex);
				logger.info("当前队列任务回调修改结果" + result);
				// 第三方回调
				thirdInterface.execute(dispatchPlan);
				// 发送短信
				smsInterface.execute(dispatchPlan, mqSuccPhoneDto);
			}
		} catch (Exception e) {
			logger.info("SuccesPhone4MQLisener消费数据有问题"+message);
			try {
				channel.basicAck(message2.getMessageProperties().getDeliveryTag(), false);
			} catch (IOException e1) {
				logger.info("SuccesPhone4MQLisener ack确认机制有问题");
			}
		}
	}

}
