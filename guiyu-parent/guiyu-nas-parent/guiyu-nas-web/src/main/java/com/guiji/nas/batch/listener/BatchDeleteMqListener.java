package com.guiji.nas.batch.listener;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.nas.service.NasService;
import com.guiji.nas.vo.AliyunReqVO;
import com.guiji.utils.JsonUtils;
import com.rabbitmq.client.Channel;


@Component
@RabbitListener(queues = "nasFileDeleteQueue", containerFactory = "BatchDeleteRabbitFactory")
public class BatchDeleteMqListener {
	
	private static Logger logger = LoggerFactory.getLogger(BatchDeleteMqListener.class);

	@Autowired
	private NasService nasService;

	@RabbitHandler
	public void process(String message,Channel channel,Message message2) {
		logger.info("删除fastdfs上文件队列接收消息:" + message);
		try {
			AliyunReqVO req = JsonUtils.json2Bean(message, AliyunReqVO.class);
			nasService.deleteByUrl(req.getSourceUrl());
			logger.info("fastdfs上文件删除成功:" + req.getSourceUrl());
		} catch (Exception e) {
			//这次消息，我已经接受并消费掉了，不会再重复发送消费
			logger.info("error",e);
			try {
				logger.info("BatchDeleteMqListener消费的数据有问题---------------------------------------"+message);
				channel.basicAck(message2.getMessageProperties().getDeliveryTag(), false);
			} catch (IOException e1) {
				logger.info("已经接受并消费掉了，不会再重复发送消费有问题了");
			}
		}
	}
}
