package com.guiji.dispatch.batchimport;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.utils.JsonUtils;

/**
 * Created by zhujy on 2018/11/17.
 */
@Service
public class BatchImportQueueHandler implements IBatchImportQueueHandlerService {

	// private static final BatchImportQueueHandler instance = new
	// BatchImportQueueHandler();

	@Autowired
	private AmqpTemplate rabbitTemplate;

	// private BatchImportQueueHandler() {
	//
	// }

	// public static BatchImportQueueHandler getInstance() {
	// return instance;
	// }

	public void add(DispatchPlan vo) {
		rabbitTemplate.convertAndSend("dispatch.filedata", JsonUtils.bean2Json(vo));
		// try {
		// BatchImortQueue.getInstance().produce(vo);
		// } catch (InterruptedException e) {
		// Thread.currentThread().interrupt();
		// }
	}

	// public void add(List<DispatchPlan> lst) {
	// try {
	// BatchImortQueue.getInstance().produce(lst);
	// } catch (InterruptedException e) {
	// Thread.currentThread().interrupt();
	// }
	// }

}
