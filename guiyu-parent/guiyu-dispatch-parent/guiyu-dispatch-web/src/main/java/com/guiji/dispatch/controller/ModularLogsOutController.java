package com.guiji.dispatch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IModularLogsOut;
import com.guiji.dispatch.model.ModularLogs;
import com.guiji.utils.JsonUtils;

@RestController
public class ModularLogsOutController implements IModularLogsOut {
	protected static Logger logger = LoggerFactory.getLogger(ModularLogsOutController.class);

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Override
	@PostMapping(value = "out/notifyLogs")
	public ReturnData<Boolean> notifyLogs(@RequestBody ModularLogs modularLogs) {
		ReturnData<Boolean> data = new ReturnData<>();
		// 第一个参数指定队列，第二个参数来指定路由的key，第三个参数指定消息
	//	rabbitTemplate.convertAndSend("dispatch.ModularLogs", JsonUtils.bean2Json(modularLogs));
		data.setBody(true);
		return data;
	}

}
