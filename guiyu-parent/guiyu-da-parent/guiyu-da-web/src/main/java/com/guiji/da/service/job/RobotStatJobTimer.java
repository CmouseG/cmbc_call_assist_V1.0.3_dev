package com.guiji.da.service.job;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.da.dao.entity.RobotCallProcessStat;
import com.guiji.da.service.impl.robot.RobotCacheService;
import com.guiji.da.service.impl.robot.RobotNewTransService;
import com.guiji.da.service.vo.RobotCallProcessStatCache;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.JsonUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

/** 
* @ClassName: AiResourceJobTimer 
* @Description: AI资源定时任务作业
* @date 2018年12月6日 上午11:34:59 
* @version V1.0  
*/
@Component
@JobHandler(value="robotStatSaveJobTimer")
public class RobotStatJobTimer  extends IJobHandler{
	@Autowired
	RobotCacheService robotCacheService;
	@Autowired
	RobotNewTransService robotNewTransService;
	
    
    /**
     * 每天晚上将缓存中的机器人话术流程分析数据落地到数据库
     */
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		long beginTime = System.currentTimeMillis();
        XxlJobLogger.log("定时任务，准备发起[落地机器人模板通话分析数据]开始...");
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
        XxlJobLogger.log("定时任务，用时{}S,[落地机器人模板通话分析数据]完成...",(endTime-beginTime)/1000);
		return null;
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
