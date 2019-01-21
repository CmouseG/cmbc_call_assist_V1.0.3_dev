package com.guiji.ccmanager.scheduler;

import com.guiji.ccmanager.service.LineReportService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@JobHandler(value="CallCenterLineReportJobHandler")
@Component
public class CallCenterLineReportJobHandler extends IJobHandler {

	@Autowired
	LineReportService lineReportService;

	@Override
	public ReturnT<String> execute(String param) throws ParseException {
		XxlJobLogger.log("呼叫中心，5分钟统计report_line线路信息开始");
		lineReportService.statisticsReportLineCode();
		XxlJobLogger.log("呼叫中心，5分钟统计report_line线路信息结束");
		return SUCCESS;
	}

}
