package com.guiji.da.service.job;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.da.dao.entity.RobotCallProcessStat;
import com.guiji.da.service.impl.robot.RobotCacheService;
import com.guiji.da.service.impl.robot.RobotNewTransService;
import com.guiji.da.service.vo.RobotCallProcessStatCache;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.JsonUtils;

/** 
* @ClassName: AiResourceJobTimer 
* @Description: AI资源定时任务作业
* @date 2018年12月6日 上午11:34:59 
* @version V1.0  
*/
@Component
public class RobotStatJobTimer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	RobotCacheService robotCacheService;
	@Autowired
	DistributedLockHandler distributedLockHandler;
	@Autowired
	RobotNewTransService robotNewTransService;
	
	
	/**
	 * 每天晚上将缓存中的机器人话术流程分析数据落地到数据库
	 */
    @Scheduled(cron="0 0 22 * * ?")
    public void aiResourRel(){
    	Lock lock = new Lock("LOCK_ROBOT_AI_RELEASE_JOB", "LOCK_ROBOT_AI_RELEASE_JOB");
    	if (distributedLockHandler.tryLock(lock,0L)) { // 默认锁设置,超时时间设置为0ms，要么获取锁，那么获取不到，不重试
    		long beginTime = System.currentTimeMillis();
            logger.info("定时任务，准备发起[落地机器人模板通话分析数据]开始...");
            //查询缓存中所有的统计数据
            Map<String,List<Map<String,RobotCallProcessStatCache>>> allMap = robotCacheService.queryAllRobotCallProcessStat(null);
            if(allMap != null && !allMap.isEmpty()) {
            	for(Map.Entry<String, List<Map<String,RobotCallProcessStatCache>>> allUserEntry : allMap.entrySet()) {
            		String userId = allUserEntry.getKey();	//用户ID
            		//用户不同模板的话术统计
            		List<Map<String,RobotCallProcessStatCache>> userTempList = allUserEntry.getValue();
            		if(userTempList != null && !userTempList.isEmpty()) {
            			for(Map<String,RobotCallProcessStatCache> userTempMap : userTempList) {
            				for(Map.Entry<String, RobotCallProcessStatCache> userTempEntry : userTempMap.entrySet()) {
            					String templateId = userTempEntry.getKey();	//模板
            					RobotCallProcessStatCache robotCallProcessStatCache = userTempEntry.getValue();
            					if(robotCallProcessStatCache != null) {
            						//数据入库
            						this.saveRobotCallProcessStat(robotCallProcessStatCache);
            					}
            				}
            			}
            		}
            		//将redis中该用户的量化分析缓存数据清空
            		robotCacheService.delUserCallStats(userId);
            	}
            }
            long endTime = System.currentTimeMillis();
            logger.info("定时任务，用时{}S,[落地机器人模板通话分析数据]完成...",(endTime-beginTime)/1000);
            distributedLockHandler.releaseLock(lock);	//释放锁
    	}else {
    		logger.warn("定时任务[落地机器人模板通话分析数据]未能获取锁！！！");
    	}
    }
    
    
    
    /**
     * 保存机器人统计数据
     * @param robotCallProcessStatCache
     */
    private void saveRobotCallProcessStat(RobotCallProcessStatCache robotCallProcessStatCache) {
    	RobotCallProcessStat robotCallProcessStat = new RobotCallProcessStat();
    	//属性拷贝
    	BeanUtil.copyProperties(robotCallProcessStatCache, robotCallProcessStat);
    	//将map转json存入数据库
    	if(robotCallProcessStatCache.getRefusedStatMap()!=null && !robotCallProcessStatCache.getRefusedStatMap().isEmpty()) {
    		robotCallProcessStat.setRefusedStat(JsonUtils.bean2Json(robotCallProcessStatCache.getRefusedStatMap()));
    	}
    	if(robotCallProcessStatCache.getHangupStatMap()!=null && !robotCallProcessStatCache.getHangupStatMap().isEmpty()) {
    		robotCallProcessStat.setHangupStat(JsonUtils.bean2Json(robotCallProcessStatCache.getHangupStatMap()));
    	}
    	if(robotCallProcessStatCache.getMatchStatMap()!=null && !robotCallProcessStatCache.getMatchStatMap().isEmpty()) {
    		robotCallProcessStat.setMatchStat(JsonUtils.bean2Json(robotCallProcessStatCache.getMatchStatMap()));
    	}
    	robotNewTransService.recordRobotCallProcessStat(robotCallProcessStat);
    }
	
}
