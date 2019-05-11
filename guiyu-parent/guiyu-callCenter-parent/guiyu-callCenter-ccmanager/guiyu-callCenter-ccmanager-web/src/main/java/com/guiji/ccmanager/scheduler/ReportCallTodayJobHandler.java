package com.guiji.ccmanager.scheduler;

import com.guiji.ccmanager.service.ReportSchedulerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value="ReportCallTodayJobHandler")
@Component
@Slf4j
public class ReportCallTodayJobHandler extends IJobHandler {

	@Autowired
	ReportSchedulerService reportSchedulerService;

	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("呼叫中心，5分钟统计report_call_today，ReportCallTodayJobHandler开始");
		log.info("开始统计");
		reportSchedulerService.reportCallTodayScheduler();
		log.info("开始结束");
		XxlJobLogger.log("呼叫中心，5分钟统计report_call_today，ReportCallTodayJobHandler结束");
		return SUCCESS;
	}

}
