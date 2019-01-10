package com.guiji.dispatch.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.bean.sendMsgDto;
import com.guiji.dispatch.controller.ModularLogsOutController;
import com.guiji.utils.JsonUtils;
import com.netflix.discovery.provider.Serializer;

@Service
public class IMessageServiceImpl implements IMessageService {
	protected static Logger logger = LoggerFactory.getLogger(IMessageServiceImpl.class);

	@Autowired
	private AmqpTemplate rabbitTemplate;
	@Override
	public boolean insertMessMq(sendMsgDto msgDto) {
		logger.info("接受insertMessMq---------------"+msgDto);
		rabbitTemplate.convertAndSend("dispatch.MessageMQ", JsonUtils.bean2Json(msgDto));
		return true;
	}

}
