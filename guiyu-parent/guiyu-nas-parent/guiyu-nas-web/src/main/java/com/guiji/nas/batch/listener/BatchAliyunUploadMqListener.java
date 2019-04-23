package com.guiji.nas.batch.listener;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.guiyu.message.component.FanoutSender;
import com.guiji.nas.property.AliyunUtil;
import com.guiji.nas.util.OssUtil;
import com.guiji.nas.vo.AliyunReqVO;
import com.guiji.nas.vo.AliyunRspVO;
import com.guiji.nas.vo.SysFileReqVO;
import com.guiji.utils.JsonUtils;
import com.rabbitmq.client.Channel;


@Component
@RabbitListener(queues = "fanoutAliyunUploadQueue", containerFactory = "batchUploadRabbitFactory")
public class BatchAliyunUploadMqListener {
	
	private static Logger logger = LoggerFactory.getLogger(BatchAliyunUploadMqListener.class);

	@Autowired
    private FanoutSender fanoutSender;

	@RabbitHandler
	public void process(String message,Channel channel,Message message2) {
		try {
			AliyunReqVO req = JsonUtils.json2Bean(message, AliyunReqVO.class);
			String fileName = OssUtil.getInstance().upload(req.getSourceUrl());
			AliyunRspVO res = new AliyunRspVO();
			res.setBusiId(req.getBusiId());
			res.setSourceUrl(req.getSourceUrl());
			res.setAliyunUrl(AliyunUtil.getAliyunBaseUrl() + fileName);
			fanoutSender.send("fanoutAliyunNoticeQueue", res.toString());
		} catch (Exception e) {
			//这次消息，我已经接受并消费掉了，不会再重复发送消费
			logger.info("error",e);
			try {
				logger.info("BatchAliyunUploadMqListener消费的数据有问题---------------------------------------"+message);
				channel.basicAck(message2.getMessageProperties().getDeliveryTag(), false);
			} catch (IOException e1) {
				logger.info("已经接受并消费掉了，不会再重复发送消费有问题了");
			}
		}
	}
}
