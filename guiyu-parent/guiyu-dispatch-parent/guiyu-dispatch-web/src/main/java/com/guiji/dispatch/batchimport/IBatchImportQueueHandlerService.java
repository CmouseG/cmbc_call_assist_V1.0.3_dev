package com.guiji.dispatch.batchimport;

import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface IBatchImportQueueHandlerService {
	public void add(DispatchPlan vo);
}
