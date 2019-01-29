package com.guiji.dispatch.blacklistmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.utils.JsonUtils;
import com.rabbitmq.client.Channel;

@Component
@RabbitListener(queues = "dispatch.blackListData")
public class BlackListMQListener {

	@Autowired
	private BlackListImport importService;
	
	@RabbitHandler
	public void process(String message, Channel channel, Message message2) {
		BlackList vo = JsonUtils.json2Bean(message, BlackList.class);
		importService.execute(vo);
	}
}
