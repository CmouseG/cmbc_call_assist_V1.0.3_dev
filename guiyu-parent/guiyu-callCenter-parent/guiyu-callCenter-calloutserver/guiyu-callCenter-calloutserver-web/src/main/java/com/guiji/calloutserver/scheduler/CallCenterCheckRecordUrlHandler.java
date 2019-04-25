package com.guiji.calloutserver.scheduler;

import com.guiji.calloutserver.service.CallStateService;
import com.guiji.calloutserver.service.CheckRecordUrlService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value="CallCenterCheckRecordUrlHandler")
@Component
public class CallCenterCheckRecordUrlHandler extends IJobHandler {

	@Autowired
	CheckRecordUrlService checkRecordUrlService;

	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("呼叫中心，检查未上传录音任务，开始");
		checkRecordUrlService.checkRecordUrl();
		XxlJobLogger.log("呼叫中心，检查未上传录音任务，结束");
		return SUCCESS;
	}

}
