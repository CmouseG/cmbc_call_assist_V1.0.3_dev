package com.guiji.ccmanager.scheduler;

import com.guiji.ccmanager.service.ReportSchedulerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value="CallCenterHourReportJobHandler")
@Component
public class CallCenterHourReportJobHandler extends IJobHandler {

	@Autowired
	ReportSchedulerService reportSchedulerService;

	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("呼叫中心，按小时统计report_call_hour，CallCenterHourReportJobHandler开始");
		reportSchedulerService.reportCallHourScheduler();
		XxlJobLogger.log("呼叫中心，按小时统计report_call_hour，CallCenterHourReportJobHandler结束");
		return SUCCESS;
	}

}
