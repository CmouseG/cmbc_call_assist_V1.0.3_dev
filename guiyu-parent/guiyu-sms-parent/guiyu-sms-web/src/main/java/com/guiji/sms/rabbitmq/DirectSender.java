package com.guiji.sms.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectSender
{
	private static Logger logger = LoggerFactory.getLogger(DirectSender.class);

	@Autowired
	private AmqpTemplate template;

	public void send(String queue, String message)
	{
		logger.info("Sender : " + message);
		this.template.convertAndSend(queue, message);
	}
}
