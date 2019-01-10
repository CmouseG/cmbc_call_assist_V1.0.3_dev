package com.guiji.dispatch.pushcallcenter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.dispatch.dao.PushRecordsMapper;
import com.guiji.dispatch.dao.entity.PushRecords;
import com.guiji.utils.JsonUtils;
import com.rabbitmq.client.Channel;

@Component
@RabbitListener(queues = "dispatch.PushPhonesRecords")
public class PushRecordsMQListener {
	private static Logger logger = LoggerFactory.getLogger(PushRecordsMQListener.class);

	@Autowired
	private PushRecordsMapper pushMapper;

	@RabbitHandler
	public void process(String message, Channel channel, Message message2) {
		try {
			PushRecords records = JsonUtils.json2Bean(message, PushRecords.class);
			pushMapper.insert(records);
		} catch (Exception e) {
			logger.info("PushRecordsMQListener消费数据有问题" + message);
			try {
				channel.basicAck(message2.getMessageProperties().getDeliveryTag(), false);
			} catch (IOException e1) {
				logger.info("PushRecordsMQListener ack确认机制有问题");
			}
		}
	}
}
