package com.guiji.dispatch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.dispatch.state.IphonesThread;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

@JobHandler(value="phoneJobHandler")
@Component
public class PhoneJobHandler extends IJobHandler {
	private static final Logger logger = LoggerFactory.getLogger(ChangePlanIntegralPointJobHandler.class);
	
	@Autowired
	private IphonesThread phoneThread;

	@Override
	public ReturnT<String> execute(String arg0) throws Exception {
		XxlJobLogger.log("XXL-JOB, PhoneJobHandler start.");
		phoneThread.execute();
		XxlJobLogger.log("XXL-JOB, PhoneJobHandler end.");
		return SUCCESS;
	}
}
