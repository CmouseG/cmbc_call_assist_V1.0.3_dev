package com.guiji.ccmanager.scheduler;

import com.guiji.ccmanager.service.ReportSchedulerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value="CallCenterDayReportJobHandler")
@Component
public class CallCenterDayReportJobHandler extends IJobHandler {

	@Autowired
	ReportSchedulerService reportSchedulerService;

	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("呼叫中心，按天统计report_call_day，CallCenterDayReportJobHandler开始");
		reportSchedulerService.reportCallDayScheduler();
		XxlJobLogger.log("呼叫中心，按天统计report_call_day，CallCenterDayReportJobHandler结束");
		return SUCCESS;
	}

}
