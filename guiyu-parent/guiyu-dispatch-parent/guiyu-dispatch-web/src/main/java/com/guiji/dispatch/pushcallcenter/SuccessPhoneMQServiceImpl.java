package com.guiji.dispatch.pushcallcenter;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.utils.JsonUtils;

@Service
public class SuccessPhoneMQServiceImpl implements SuccessPhoneMQService {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	/**
	 * 回调之后写入业务队列中处理回调业务
	 */
	@Override
	public boolean insertSuccesPhone4BusinessMQ(MQSuccPhoneDto mqSuccPhoneDto) {
		rabbitTemplate.convertAndSend("dispatch.SuccessPhoneMQ", JsonUtils.bean2Json(mqSuccPhoneDto));
		return true;
	}

	/**
	 * 回调之后写入调度队列中，主动询问呼叫中心当前负载情况
	 */
	@Override
	public boolean insertCallBack4MQ(MQSuccPhoneDto mqSuccPhoneDto) {
		rabbitTemplate.convertAndSend("dispatch.CallBackEvent", JsonUtils.bean2Json(mqSuccPhoneDto));
		return true;
	}

}
