package com.guiji.ccmanager.scheduler;

import com.guiji.ccmanager.service.ReportSchedulerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value="CallCenterCallLineDayReportHandler")
@Component
public class CallCenterCallLineDayReportHandler extends IJobHandler {

	@Autowired
	ReportSchedulerService reportSchedulerService;

	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("呼叫中心，按天统计call_line_day_report，CallCenterCallLineDayReportHandler开始");
		reportSchedulerService.reportCallLineDayReportScheduler();
		XxlJobLogger.log("呼叫中心，按天统计call_line_day_report，CallCenterCallLineDayReportHandler结束");
		return SUCCESS;
	}

}
