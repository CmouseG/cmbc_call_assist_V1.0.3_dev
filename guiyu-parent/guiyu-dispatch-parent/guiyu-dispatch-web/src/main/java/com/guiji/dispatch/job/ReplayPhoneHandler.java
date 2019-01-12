package com.guiji.dispatch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

@JobHandler(value = "replayPhoneHandler")
@Component
public class ReplayPhoneHandler extends IJobHandler {

	private static final Logger logger = LoggerFactory.getLogger(ReplayPhoneHandler.class);

	@Autowired
	private IDispatchPlanService dispatchPlanService;

	@Autowired
	DistributedLockHandler distributedLockHandler;

	
	
	
	@Override
	public ReturnT<String> execute(String arg0) throws Exception {
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------");
		dispatchPlanService.updateReplayDate(true);
		dispatchPlanService.updateReplayDate(false);
		return SUCCESS;
	}

}
