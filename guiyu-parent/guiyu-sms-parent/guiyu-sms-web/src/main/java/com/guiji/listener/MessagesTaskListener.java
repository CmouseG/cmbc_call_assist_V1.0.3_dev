package com.guiji.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.entity.SmsConstants;
import com.guiji.model.TaskReq;
import com.guiji.service.SendSmsService;
import com.guiji.sms.dao.SmsTaskMapper;
import com.guiji.sms.dao.entity.SmsTask;
import com.guiji.sms.dao.entity.SmsTaskExample;
import com.guiji.utils.JsonUtils;

@Component
@RabbitListener(queues = "MessagesTaskMQ.Sms")
public class MessagesTaskListener
{
	private static final Logger logger = LoggerFactory.getLogger(MessagesTaskListener.class);
	
	@Autowired
	SendSmsService sendSmsService;
	@Autowired
	SmsTaskMapper taskMapper;
	
	@RabbitHandler
	public void process(String message) throws Exception
	{
		SmsTask smsTask = null;
		try
		{
			TaskReq taskReq = JsonUtils.json2Bean(message, TaskReq.class);
			logger.info(taskReq.toString());
			sendSmsService.preSendMsg(taskReq); // 发送
			SmsTaskExample example = new SmsTaskExample();
			example.createCriteria().andTaskNameEqualTo(taskReq.getTaskName())
									.andTunnelNameEqualTo(taskReq.getTunnelName())
									.andSendDateEqualTo(taskReq.getSendTime())
									.andCompanyNameEqualTo(taskReq.getCompanyName());
			smsTask = taskMapper.selectByExampleWithBLOBs(example).get(0);
			smsTask.setSendStatus(SmsConstants.End); // 2-已结束
		} catch (Exception e) {
			smsTask.setSendStatus(SmsConstants.Fail); // 3-发送失败
		}
		taskMapper.updateByPrimaryKeySelective(smsTask);
	}
}
