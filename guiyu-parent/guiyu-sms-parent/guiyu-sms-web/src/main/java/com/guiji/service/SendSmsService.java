package com.guiji.service;

import com.guiji.model.TaskReq;
import com.guiji.sms.vo.SendMReqVO;

public interface SendSmsService
{	
	/**
	 * 将请求推送到MQ
	 */
	public void pushReqToMQ(SendMReqVO sendMReq);
	
	/**
	 * 将群发任务推送到MQ
	 */
	public void pushTaskToMQ(TaskReq taskReq);
	
	/**
	 * 群发短信
	 */
	public void preSendMsg(TaskReq taskReq) throws Exception;
	
}
