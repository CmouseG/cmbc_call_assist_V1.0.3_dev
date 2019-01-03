package com.guiji.dispatch.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.dispatch.service.SuccessPhoneMQService;
import com.guiji.utils.JsonUtils;
@Service
public class SuccessPhoneMQServiceImpl implements SuccessPhoneMQService {

	@Autowired
	private AmqpTemplate rabbitTemplate;


	@Override
	public boolean insertSuccesPhone4MQ(MQSuccPhoneDto mqSuccPhoneDto) {
		rabbitTemplate.convertAndSend("dispatch.SuccessPhoneMQ", JsonUtils.bean2Json(mqSuccPhoneDto));
		return true;
	}

}
