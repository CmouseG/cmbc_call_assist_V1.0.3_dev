package com.guiji.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.model.TaskReq;
import com.guiji.service.SendSmsService;
import com.guiji.service.TaskService;
import com.guiji.sms.dao.entity.SmsTask;
import com.guiji.utils.ListUtil;
import com.guiji.utils.RedisUtil;

@Component
public class SendMsgAtTimeHandler
{
	private static final Logger logger = LoggerFactory.getLogger(SendMsgAtTimeHandler.class);
	
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	TaskService taskService;
	@Autowired
	SendSmsService sendSmsService;
	
	@Scheduled(fixedRate = 1000*60*1) //每分钟执行一次
	public void task() throws Exception
	{
		List<SmsTask> taskList = taskService.getTimeTaskList();
		if(taskList == null || taskList.isEmpty()){
			logger.info("没有可执行的短信发送任务");
			return;
		}
		
		for(SmsTask task : taskList)
		{
			if(task.getAuditingStatus() == 0)
			{
				logger.info("短信内容未审核，暂不能发送！");
				continue;
			}
			if(task.getRunStatus() == 0)
			{
				logger.info("短信任务已停止，暂不发送！");
				continue;
			}
			List<Object> phoneList = redisUtil.lGet(task.getTaskName(), 0, -1);
			//组装发送请求
			TaskReq taskReq = new TaskReq(task.getTaskName(), task.getSendType(), 
					ListUtil.convertObjectToPhone(phoneList), task.getTunnelName(), task.getSmsTemplateId(), task.getSmsContent());
			taskReq.setSendTime(task.getSendDate());
			sendSmsService.sendMessages(taskReq); //群发短信
		}
		
	}

}
