package com.guiji.dispatch.thirdapi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.utils.JsonUtils;
import com.rabbitmq.client.Channel;

@Component
@RabbitListener(queues = "dispatch.thirdApiData")
public class ThirdApiImportMQListener {

	static Logger logger = LoggerFactory.getLogger(ThirdApiImportMQListener.class);

	private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
	@Autowired
	private IThirdApiImportRecordHandler handler;

	@RabbitHandler
	public void process(String message, Channel channel, Message message2) {
		logger.info("ThirdApiImportMQListener："+"message");
		DispatchPlan vo = JsonUtils.json2Bean(message, DispatchPlan.class);
		executorService.execute(new BatchImportThread(vo, handler));
	}

	class BatchImportThread implements Runnable {
		private DispatchPlan vo;

		private IThirdApiImportRecordHandler handler;

		protected BatchImportThread(DispatchPlan vo, IThirdApiImportRecordHandler handler) {
			this.vo = vo;
			this.handler = handler;
		}

		@Override
		public void run() {
			try {
				this.handler.excute(vo);
			} catch (Exception e) {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>ThirdApiImportMQListener报错了",e);
			}
		}
	}
}
