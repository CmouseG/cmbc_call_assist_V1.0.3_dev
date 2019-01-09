package com.guiji.robot.service.job;

import java.util.List;
import java.util.Map;

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
		XxlJobLogger.log("定时任务，准备发起[释放全量已分配机器人]开始...");
		//查询所有用户已分配的机器人列表
		Map<String,List<AiInuseCache>> allUserAiInUserMap = aiCacheService.queryAllAiInUse();
		if(allUserAiInUserMap != null && !allUserAiInUserMap.isEmpty()) {
			for(Map.Entry<String, List<AiInuseCache>> allUserAiInuseEntry : allUserAiInUserMap.entrySet()) {
				String userId = allUserAiInuseEntry.getKey();	//用户ID
				List<AiInuseCache> aiList = allUserAiInuseEntry.getValue();	//用户已分配的机器人
				XxlJobLogger.log("开始释放用户{}[{}]个机器人...",userId,aiList==null?0:aiList.size());
				//释放机器人资源
				iAiResourceManagerService.aiBatchRelease(aiList);
				XxlJobLogger.log("释放用户{}[{}]个机器人...完成",userId,aiList==null?0:aiList.size());
			}
		}
		long endTime = System.currentTimeMillis();
		XxlJobLogger.log("定时任务，用时{}S,[释放全量已分配机器人]完成...",(endTime-beginTime)/1000);
		return null;
	}
	
}
