package com.guiji.botsentence.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;
import com.guiji.utils.JsonUtils;

@Component
@RabbitListener(queues = "fanoutPublishBotstence.SELLBOT")
public class SellbotReceiver {

	protected static Logger logger=LoggerFactory.getLogger(SellbotReceiver.class); 

	@Autowired
	private UpdateReceiverResolver resolver;
	
	@RabbitHandler
	public void process(String message) {
		try {
			logger.info("接收部署回调...");
			logger.info("response报文: " + message);
			PublishBotstenceResultMsgVO publishBotstenceResultMsgVO = JsonUtils.json2Bean(message,PublishBotstenceResultMsgVO.class);
			resolver.resolver(publishBotstenceResultMsgVO);
		}catch(Exception e) {
			logger.error("接收部署回调殿堂...", e);
		}
		
	}

}
