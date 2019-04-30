package com.guiji.dispatch.mq;

import com.guiji.dispatch.service.PlanTableService;
import com.guiji.guiyu.message.component.FanoutSender;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "AddOrgNotice.fanout.Dispatch")
public class CreateTableOfShardingMQListener
{

	private static final Logger logger = LoggerFactory.getLogger(CreateTableOfShardingMQListener.class);

	@Autowired
	private PlanTableService planTableService;

	@Autowired
	private FanoutSender fanoutSender;

	@RabbitHandler
	public void process(String message, Channel channel, Message message2) {

		logger.info("CreateTableOfShardingMQListener>>>>>>>>>>>>>>>>>>>"+message);
		if(StringUtils.isEmpty(message)){
			logger.info("当前CreateTableOfShardingMQListener消费数据有问题");
			return;
		}
		Integer orgId = Integer.valueOf(message);

		if(orgId <= 0)
		{
			return;
		}

		try
		{
			planTableService.createPlanTable(orgId);

			fanoutSender.send("fanout.dispatch.creatOrgDone", "" + orgId);
		} catch (Exception e)
		{
			logger.error(e.getMessage());
		}
	}
}
