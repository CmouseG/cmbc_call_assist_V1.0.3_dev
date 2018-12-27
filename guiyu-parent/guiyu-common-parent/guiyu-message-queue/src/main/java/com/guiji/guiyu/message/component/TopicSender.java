package com.guiji.guiyu.message.component;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {
 
	protected static Logger logger=LoggerFactory.getLogger(TopicSender.class);
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
 
	public void send(String queue,String topic,String msg) {
		logger.debug("Sender : " + msg.toString());
		this.rabbitTemplate.convertAndSend(queue,topic,msg);
	}
 
}
