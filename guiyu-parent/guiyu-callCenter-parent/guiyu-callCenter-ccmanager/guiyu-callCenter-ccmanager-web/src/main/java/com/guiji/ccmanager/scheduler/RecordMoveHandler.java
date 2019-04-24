package com.guiji.ccmanager.scheduler;

import com.guiji.ccmanager.service.CallOutRecordUrlUpdateService;
import com.guiji.ccmanager.service.ReportSchedulerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value="RecordMoveHandler")
@Component
public class RecordMoveHandler extends IJobHandler {

	@Autowired
	CallOutRecordUrlUpdateService callOutRecordUrlUpdateService;

	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("呼叫中心，定时任务，往mq推送原有录音地址，开始");
		callOutRecordUrlUpdateService.updateCallOutRecordUrl();
		callOutRecordUrlUpdateService.updateDetailCustomerUrl();
		XxlJobLogger.log("呼叫中心，定时任务，往mq推送原有录音地址，结束");
		return SUCCESS;
	}

}
