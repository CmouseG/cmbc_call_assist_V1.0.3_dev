package com.guiji.dispatch.mq;

import java.util.List;

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
	private static Logger logger = LoggerFactory.getLogger(ModularMqListener.class);

	@Autowired
	private ModularLogsMapper modularLogsMapper;
	
	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;

	@RabbitHandler
	public void process(String message) {
		ModularLogs logs = JsonUtils.json2Bean(message, ModularLogs.class);
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(logs.getPlanUuid());
		 List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(ex);
		 DispatchPlan dispatchPlan = null;
		 if(selectByExample.size()>0){
			 dispatchPlan = selectByExample.get(0);
		 }else{
			 logger.info("接受MQ消息有问题了");
		 }
		logs.setBatchName(dispatchPlan.getBatchName());
		modularLogsMapper.insert(logs);
	}
}
