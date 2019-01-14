package com.guiji.robot.service.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.robot.service.IAiResourceManagerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

/** 
* @ClassName: AiResourApplyJobTimer 
* @Description: 定时从进程管理申请机器人
* @date 2019年1月14日 下午3:20:43 
* @version V1.0  
*/
@Component
@JobHandler(value="aiResourApplyJobTimer")
public class AiResourApplyJobTimer extends IJobHandler{
	@Autowired
	IAiResourceManagerService iAiResourceManagerService;
	
	/**
	 * 每天早上定时到进程管理申请机器人
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		long beginTime = System.currentTimeMillis();
		XxlJobLogger.log("定时任务，准备发起[到进程管理申请机器人]开始...");
		//查询所有用户已分配的机器人列表
		iAiResourceManagerService.reloadSellbotResource();
		long endTime = System.currentTimeMillis();
		XxlJobLogger.log("定时任务，用时{}S,[到进程管理申请机器人]完成...",(endTime-beginTime)/1000);
		return SUCCESS;
	}
	
}
