package com.guiji.sms.rabbitmq;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig
{
	@Value("${rabbit.general.direct.queues:}")
	private String directQueues;

	@Autowired
	ConnectionFactory connectionFactory;

	/**
	 * 动态生成队列
	 */
	@Bean
	public String[] createQueues() throws AmqpException, IOException
	{
		String[] queues = null;
		if (StringUtils.isNotEmpty(directQueues)) 
		{
			queues = directQueues.split("\\|");
			for (int i = 0; i < queues.length; i++) 
			{
				connectionFactory.createConnection()
								 .createChannel(false)
								 .queueDeclare(queues[i], true, false, false, null);
			}
		}
		return queues;
	}
}
