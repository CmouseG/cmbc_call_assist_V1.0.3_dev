package com.guiji.guiyu.message.component;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutSender {
 
	protected static Logger logger=LoggerFactory.getLogger(FanoutSender.class); 
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
 
	public void send(String fanoutExchange,String msg) {
		//String context = "hi, fanout msg ";
		logger.debug("Sender : " + msg.toString());
		this.rabbitTemplate.convertAndSend(fanoutExchange,"", msg);
	}
 
}
