package com.guiji.dispatch.impl;

import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.model.ModularLogs;
import com.guiji.dispatch.service.IModularLogsService;
import com.guiji.utils.JsonUtils;

@Service
public class IModularLogsServiceImpl implements IModularLogsService {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Override
	public boolean notifyLogs(ModularLogs modularLogs) {
		// 第一个参数指定队列，第二个参数来指定路由的key，第三个参数指定消息
	//	rabbitTemplate.convertAndSend("dispatch.ModularLogs", JsonUtils.bean2Json(modularLogs));
		return true;
	}

	@Override
	public boolean notifyLogsList(List<ModularLogs> modularLogsList) {
		/*for (ModularLogs logs : modularLogsList) {
			rabbitTemplate.convertAndSend("dispatch.ModularLogs", JsonUtils.bean2Json(logs));
		}*/
		return true;
	}

}
