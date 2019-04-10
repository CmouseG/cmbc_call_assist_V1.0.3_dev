package com.guiji.dispatch.batchimport.listener;

import com.guiji.dispatch.batchimport.BatchImportRecordHandlerImpl;
import com.guiji.dispatch.batchimport.IBatchImportRecordHandler;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.utils.JsonUtils;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "dispatch.filedata", containerFactory = "batchImportRabbitFactory")
public class BatchImportMQListener {

	private static final Logger logger = LoggerFactory.getLogger(BatchImportRecordHandlerImpl.class);

	@Autowired
	private IBatchImportRecordHandler handler;

	@RabbitHandler
	public void process(String message, Channel channel, Message message2) {
		logger.info(message);
		DispatchPlan vo = JsonUtils.json2Bean(message, DispatchPlan.class);
		//executorService.execute(new BatchImportThread(vo, handler));
		try
		{
			handler.preCheck(vo);
			//logger.info("结束异步调用，{}", System.currentTimeMillis() - start);
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("", e);
		}

	}
	//
	//	class BatchImportThread implements Runnable {
	//		private DispatchPlan vo;
	//
	//		private IBatchImportRecordHandler handler;
	//
	//		protected BatchImportThread(DispatchPlan vo, IBatchImportRecordHandler handler) {
	//			this.vo = vo;
	//			this.handler = handler;
	//		}
	//
	//		@Override
	//		public void run() {
	//			try {
	//
	//				handler.excute(vo);
	//
	//			} catch (Exception e) {
	//				e.printStackTrace();
	//				logger.info("现在已经正在消费第{}个", Stat.INSTANCE.getCount());
	//				logger.info(e.getMessage());
	//				logger.error("批量导入处理发生消费异常", Stat.INSTANCE.getCount(), e);
	//			}
	//		}
	//	}
}
