package com.guiji.guiyu.message.component;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {
 
	protected static Logger logger=LoggerFactory.getLogger(QueueSender.class);
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
 
	public void send(String queue,String msg) {
		logger.debug("Sender : " + msg.toString());
		this.rabbitTemplate.convertAndSend(queue,msg);
	}
 
}
