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
	 * 群发短信
	 */
	public void sendMessages(TaskReq taskReq);
	
}
