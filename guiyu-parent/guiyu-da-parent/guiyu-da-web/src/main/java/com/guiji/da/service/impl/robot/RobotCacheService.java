package com.guiji.da.service.impl.robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.da.constants.DaConstants;
import com.guiji.da.service.vo.RobotCallProcessStatCache;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: AiCacheService 
* @Description: AI数据缓存服务
* @date 2018年11月19日 下午5:27:28 
* @version V1.0  
*/
@Service
public class RobotCacheService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	RedisUtil redisUtil;

	
	
	/**
	 * 更新缓存数据
	 * @return
	 */
	public void cacheRobotCallProcessStat(String userId,String templateId,Map<String,RobotCallProcessStatCache> map) {
		if(StrUtils.isNotEmpty(userId) && StrUtils.isNotEmpty(templateId)) {
			redisUtil.hset(DaConstants.DA_CALL_PROCESS_STAT_+userId, templateId, map);
		}
	}
	
	/**
	 * 查询用户缓存中的sellbot统计数据
	 * @param userId
	 * @return
	 */
	public List<Map<String,RobotCallProcessStatCache>> queryUserCallStatList(String userId){
		Map<Object,Object> allMap = redisUtil.hmget(DaConstants.DA_CALL_PROCESS_STAT_+userId);
		if(allMap != null && !allMap.isEmpty()) {
			List<Map<String,RobotCallProcessStatCache>> list = new ArrayList<Map<String,RobotCallProcessStatCache>>();
			for (Map.Entry<Object,Object> callStatEntry : allMap.entrySet()) { 
				Map<String,RobotCallProcessStatCache> sentenceMap = (Map<String,RobotCallProcessStatCache>) callStatEntry.getValue();
				list.add(sentenceMap);
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 查询某个用户的某个模板的统计数据
	 * @param userId
	 * @param templateId
	 * @return
	 */
	public Map<String,RobotCallProcessStatCache> queryUserCallStatByUserAndTemplate(String userId,String templateId){
		if(StrUtils.isNotEmpty(userId) && StrUtils.isNotEmpty(templateId)) {
			Object cacheObj = redisUtil.hget(DaConstants.DA_CALL_PROCESS_STAT_+userId, templateId);
			if(cacheObj != null) {
				return (Map<String,RobotCallProcessStatCache>) cacheObj;
			}
		}
		return null;
	}
	
	
	/**
	 * 删除某个用户所有统计缓存数据
	 * @param userId
	 */
	public void delUserCallStats(String userId) {
		redisUtil.del(DaConstants.DA_CALL_PROCESS_STAT_+userId);
	}
	
	
	/**
	 * 删除某个用户某个话术模板的统计缓存数据
	 * @param userId
	 */
	public void delUserTemplateCallStat(String userId,String templateId) {
		redisUtil.hdel(DaConstants.DA_CALL_PROCESS_STAT_+userId,templateId);
	}
	
	
	/**
	 * 查询缓存中所有机器人通话流程统计
	 * @return
	 */
	public Map<String,List<Map<String,RobotCallProcessStatCache>>> queryAllRobotCallProcessStat(String templateId){
		Map<String,List<Map<String,RobotCallProcessStatCache>>> allRobotCallStatMap = new HashMap<String,List<Map<String,RobotCallProcessStatCache>>>();
		//查询所有在缓存中的用户数据
		Set<String> userSet = redisUtil.getAllKeyMatch(DaConstants.DA_CALL_PROCESS_STAT_);
		if(userSet != null && !userSet.isEmpty()) {
			for(String key:userSet) {
				String userId = key.substring(key.indexOf(DaConstants.DA_CALL_PROCESS_STAT_)+21);	//将key中的userid截取出来
				if(StrUtils.isNotEmpty(templateId)) {
					//模板不为空，使用userid-template进行查询
					Map<String,RobotCallProcessStatCache> map = this.queryUserCallStatByUserAndTemplate(userId, templateId);
					if(map != null && !map.isEmpty()) {
						List<Map<String,RobotCallProcessStatCache>> list = new ArrayList<Map<String,RobotCallProcessStatCache>>();
						list.add(map);
						allRobotCallStatMap.put(userId, list);
					}
				}else {
					//模板为空，使用userId查询
					List<Map<String,RobotCallProcessStatCache>> list = this.queryUserCallStatList(userId);
					allRobotCallStatMap.put(userId, list);
				}
			}
		}
		return allRobotCallStatMap;
	}
	
	
}
