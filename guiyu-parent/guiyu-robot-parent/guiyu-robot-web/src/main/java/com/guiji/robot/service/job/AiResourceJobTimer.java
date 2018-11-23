package com.guiji.robot.service.job;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.robot.service.IAiResourceManagerService;
import com.guiji.robot.service.impl.AiCacheService;
import com.guiji.robot.service.vo.AiInuseCache;

/** 
* @ClassName: AiResourceJobTimer 
* @Description: AI资源定时任务作业
* @date 2018年11月23日 上午11:34:59 
* @version V1.0  
*/
@Component
public class AiResourceJobTimer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	AiCacheService aiCacheService; 
	@Autowired
	IAiResourceManagerService iAiResourceManagerService;
	@Autowired
	DistributedLockHandler distributedLockHandler;
	
	/**
	 * 每天晚上释放分配给用户的机器人
	 */
//    @Scheduled(cron="0 30 20 * * ?")
	@Scheduled(cron="0 08 14 * * ?")
    public void aiResourRel(){
    	Lock lock = new Lock("LOCK_ROBOT_AI_RELEASE_JOB", "LOCK_ROBOT_AI_RELEASE_JOB");
    	if (distributedLockHandler.tryLock(lock, 1, 10, 30*60*1000)) { // 尝试1ms,每10ms尝试一次，持锁时间为30分钟
    		long beginTime = System.currentTimeMillis();
            logger.info("定时任务，准备发起[释放全量已分配机器人]开始...");
            //查询所有用户已分配的机器人列表
            Map<String,List<AiInuseCache>> allUserAiInUserMap = aiCacheService.queryAllAiInUse();
            if(allUserAiInUserMap != null && !allUserAiInUserMap.isEmpty()) {
            	for(Map.Entry<String, List<AiInuseCache>> allUserAiInuseEntry : allUserAiInUserMap.entrySet()) {
            		String userId = allUserAiInuseEntry.getKey();	//用户ID
            		List<AiInuseCache> aiList = allUserAiInuseEntry.getValue();	//用户已分配的机器人
            		logger.info("开始释放用户{}[{}]个机器人...",userId,aiList==null?0:aiList.size());
            		//释放机器人资源
            		iAiResourceManagerService.aiBatchRelease(aiList);
            		logger.info("释放用户{}[{}]个机器人...完成",userId,aiList==null?0:aiList.size());
            	}
            }
            long endTime = System.currentTimeMillis();
            logger.info("定时任务，用时{}S,[释放全量已分配机器人]完成...",(endTime-beginTime)/1000);
            distributedLockHandler.releaseLock(lock);	//释放锁
    	}else {
    		logger.warn("定时任务[晚间释放机器人]未能获取锁！！！");
    	}
    }
}
