package com.guiji.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(MqReqListener.class);
	
	@Autowired
	ReqHandler reqHandler;
	
	@RabbitHandler
	public void process(String message)
	{
		try
		{
			SendMReqVO sendMReq = JsonUtils.json2Bean(message, SendMReqVO.class);
			logger.info(sendMReq.toString());
			reqHandler.handleReq(sendMReq);
		} catch (Exception e){
			logger.error("处理失败!", e);
		}
	}
}
