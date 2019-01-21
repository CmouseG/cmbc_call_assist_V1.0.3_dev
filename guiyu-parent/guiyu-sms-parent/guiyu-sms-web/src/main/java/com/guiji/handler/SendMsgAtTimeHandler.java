package com.guiji.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.model.TaskReq;
import com.guiji.service.SendSmsService;
import com.guiji.service.TaskService;
import com.guiji.sms.dao.entity.SmsTask;
import com.guiji.utils.ListUtil;
import com.guiji.utils.RedisUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

@JobHandler(value="SendMsgAtTimeHandler")
@Component
public class SendMsgAtTimeHandler extends IJobHandler
{
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	TaskService taskService;
	@Autowired
	SendSmsService sendSmsService;

	@Override
	public ReturnT<String> execute(String param) throws Exception
	{
		List<SmsTask> taskList = taskService.getTimeTaskList();
		if(taskList == null || taskList.isEmpty()){
			XxlJobLogger.log("没有可执行的短信发送任务");
			return SUCCESS;
		}
		
		for(SmsTask task : taskList)
		{
			if(task.getAuditingStatus() == 0)
			{
				XxlJobLogger.log("短信内容未审核，暂不能发送！");
				continue;
			}
			if(task.getRunStatus() == 0)
			{
				XxlJobLogger.log("短信任务已停止，暂不发送！");
				continue;
			}
			List<Object> phoneList = redisUtil.lGet(task.getTaskName(), 0, -1);
			//组装发送请求
			TaskReq taskReq = new TaskReq(task.getTaskName(), task.getSendType(), 
					ListUtil.convertObjectToPhone(phoneList), task.getTunnelName(), task.getSmsTemplateId(), task.getSmsContent());
			taskReq.setSendTime(task.getSendDate());
			sendSmsService.sendMessages(taskReq); //群发短信
		}
		return SUCCESS;
	}

}
