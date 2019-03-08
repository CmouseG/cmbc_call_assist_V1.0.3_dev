package com.guiji.robot.service.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.impl.AiCacheService;
import com.guiji.robot.service.vo.AiInuseCache;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

/** 
* @ClassName: AiResourRelJobTimer 
* @Description: 定时自动释放机器人
* @date 2019年1月9日 下午3:20:43 
* @version V1.0  
*/
@Component
@JobHandler(value="aiResourRelJobTimer")
public class AiResourRelJobTimer extends IJobHandler{
	@Autowired
	AiCacheService aiCacheService; 
	@Autowired
	IAiResourceManagerService iAiResourceManagerService;
	
	/**
	 * 每天晚上释放分配给用户的机器人
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		long beginTime = System.currentTimeMillis();
		XxlJobLogger.log("定时任务，准备发起[释放全量机器人池]开始...");
		//将机器人还回进程管理
		List<AiInuseCache> aiPoolList = iAiResourceManagerService.queryAiPoolList();
		iAiResourceManagerService.aiBatchRtnProcess(aiPoolList);
		//清空缓存机器人资源池
		aiCacheService.delAiPools();
		int num = aiPoolList==null?0:aiPoolList.size();
		XxlJobLogger.log("释放全量机器人，将机器人还回资源池，机器人总数：{}",num);
		long endTime = System.currentTimeMillis();
		XxlJobLogger.log("定时任务，用时{}S,[释放全量机器人池]完成...",(endTime-beginTime)/1000);
		return SUCCESS;
	}
	
}
