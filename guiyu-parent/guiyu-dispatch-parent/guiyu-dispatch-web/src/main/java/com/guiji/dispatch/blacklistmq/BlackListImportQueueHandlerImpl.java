package com.guiji.dispatch.blacklistmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.utils.JsonUtils;

@Service
public class BlackListImportQueueHandlerImpl implements BlackListImportQueueHandler {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Override
	public void add(BlackList vo) {
		rabbitTemplate.convertAndSend("dispatch.blackListData", JsonUtils.bean2Json(vo));
	}

}
