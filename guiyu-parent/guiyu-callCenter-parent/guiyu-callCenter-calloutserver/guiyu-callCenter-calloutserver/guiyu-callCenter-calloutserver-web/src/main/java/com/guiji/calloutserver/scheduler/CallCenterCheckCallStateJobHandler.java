package com.guiji.calloutserver.scheduler;

import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.service.CallStateService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value="CallCenterCheckCallStateJobHandler")
@Component
public class CallCenterCheckCallStateJobHandler extends IJobHandler {

	@Autowired
	CallStateService callStateService;

	@Override
	public ReturnT<String> execute(String param) {
		XxlJobLogger.log("呼叫中心，5分钟检查一个callplan状态，CallCenterCheckCallStateJobHandler开始");
		callStateService.updateCallState();
		XxlJobLogger.log("呼叫中心，5分钟检查一个callplan状态，CallCenterCheckCallStateJobHandler结束");
		return SUCCESS;
	}

}
