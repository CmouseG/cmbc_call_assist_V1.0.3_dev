package com.guiji.ccmanager.scheduler;

import com.guiji.ccmanager.service.ReportSchedulerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value="CallCenterTruncateReportJobHandler")
@Component
public class CallCenterTruncateReportJobHandler extends IJobHandler {

	@Autowired
	ReportSchedulerService reportSchedulerService;

	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("呼叫中心，按天清空report_call_today，CallCenterTruncateReportJobHandler开始");
		reportSchedulerService.reportCallTodayTruncate();
		XxlJobLogger.log("呼叫中心，按天清空report_call_today，CallCenterTruncateReportJobHandler结束");
		return SUCCESS;
	}

}
