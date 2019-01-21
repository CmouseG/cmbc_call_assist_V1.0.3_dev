package com.guiji.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.handler.ReqHandler;
import com.guiji.sms.vo.SendMReqVO;
import com.guiji.utils.JsonUtils;

@Component
@RabbitListener(queues = "SendMessageMQ.Sms")
public class MqReqListener
{
	@Autowired
	ReqHandler reqHandler;
	
	@RabbitHandler
	public void process(String message) 
	{
		SendMReqVO sendMReq = JsonUtils.json2Bean(message, SendMReqVO.class);
		reqHandler.handleReq(sendMReq);
	}
}
