package com.guiji.dispatch.thirdapi;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.batchimport.IBatchImportQueueHandlerService;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.utils.JsonUtils;
@Service
public class ThirdApiImportQueueHandler implements IThirdApiImportQueueHandlerService {


	@Autowired
	private AmqpTemplate rabbitTemplate;


	public void add(DispatchPlan vo) {
		rabbitTemplate.convertAndSend("dispatch.thirdApiData", JsonUtils.bean2Json(vo));
	}
}
