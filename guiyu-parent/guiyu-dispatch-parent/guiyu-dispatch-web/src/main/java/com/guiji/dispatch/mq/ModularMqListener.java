package com.guiji.dispatch.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.ModularLogsMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.dao.entity.ModularLogs;
import com.guiji.utils.JsonUtils;

@Component
@RabbitListener(queues = "dispatch.ModularLogs")
public class ModularMqListener {
	@Autowired
	private ModularLogsMapper modularLogsMapper;
	
	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;

	@RabbitHandler
	public void process(String message) {
		ModularLogs logs = JsonUtils.json2Bean(message, ModularLogs.class);
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(logs.getPlanUuid());
		DispatchPlan dispatchPlan = dispatchPlanMapper.selectByExample(ex).get(0);
		logs.setBatchName(dispatchPlan.getBatchName());
		modularLogsMapper.insert(logs);
	}
}
