package com.guiji.robot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.UserResourceCache;
import com.guiji.robot.service.web.controller.User;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: AiCacheService 
* @Description: AI数据缓存服务
* @date 2018年11月19日 下午5:27:28 
* @version V1.0  
*/
@Service
public class AiCacheService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	IUserAiCfgService iUserAiCfgService;

	/**
	 * 根据用户id查询用户的资源缓存信息
	 * 如果用户缓存不存在，那么重新查询后设置环境
	 * @param userId
	 * @return
	 */
	public UserResourceCache queryUserResource(String userId) {
		Object cacheObj = redisUtil.hget(RobotConstants.ROBOT_USER_RESOURCE, userId);
		if(cacheObj == null) {
			//重新查询
			List<UserAiCfgInfo> list = iUserAiCfgService.queryUserAiCfgListByUserId(userId);
			UserResourceCache userResourceCache = new UserResourceCache();
			int aiNum = 0;
			if(ListUtil.isNotEmpty(list)) {
				for(UserAiCfgInfo cfg : list) {
					if(RobotConstants.USER_CFG_STATUS_S.equals(cfg.getStatus())) {
						//如果账户是正常状态
						aiNum = aiNum + cfg.getAiNum();
					}
				}
			}
			userResourceCache.setUserId(userId);
			userResourceCache.setAiNum(aiNum);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(userId, userResourceCache);
			//提交到redis
			redisUtil.hmset(RobotConstants.ROBOT_USER_RESOURCE, map);
			return userResourceCache;
		}else {
			return (UserResourceCache)cacheObj;
		}
	}
	
	
	/**
	 * 将分配好的机器人放入缓存
	 * userid{aiNo-aiInuse}
	 * @param userId
	 * @param list
	 * @return
	 */
	public void cacheUserAiAssign(String userId,List<AiInuseCache> list){
		if(StrUtils.isNotEmpty(userId) && ListUtil.isNotEmpty(list)) {
			Map<String,Object> map = new HashMap<String,Object>();
			for(AiInuseCache aiInuse : list) {
				map.put(aiInuse.getAiNo(), aiInuse);
			}
			redisUtil.hmset(RobotConstants.ROBOT_ASSIGN_AI+userId, map);
		}
	}
	
	/**
	 * 查询用户现在已分配的机器人
	 * @param userId
	 * @return
	 */
	public List<AiInuseCache> queryUserAiInUseList(String userId){
		Map<Object,Object> allMap = redisUtil.hmget(RobotConstants.ROBOT_ASSIGN_AI+userId);
		if(allMap != null && !allMap.isEmpty()) {
			List<AiInuseCache> list = new ArrayList<AiInuseCache>();
			for (Map.Entry<Object,Object> aiEntry : allMap.entrySet()) { 
				AiInuseCache aiInuse = (AiInuseCache) aiEntry.getValue();
				list.add(aiInuse);
			}
			return list;
		}
		return null;
	}
	
	
	/**
	 * 根据用户ID和机器人编号查询某个在使用的机器人信息
	 * @param userId
	 * @param aiNo
	 * @return
	 */
	public AiInuseCache queryAiInuse(String userId,String aiNo) {
		//查询某个ai的值
		Object cacheObj = redisUtil.hget(RobotConstants.ROBOT_ASSIGN_AI+userId, aiNo);
		if(cacheObj != null) {
			return (AiInuseCache) cacheObj;
		}
		return null;
	}
	
	
	/**
	 * 更新缓存数据状态
	 * @param aiInuse
	 * @return
	 */
	public void changeAiInUse(AiInuseCache aiInuse) {
		redisUtil.hset(RobotConstants.ROBOT_ASSIGN_AI+aiInuse.getUserId(), aiInuse.getAiNo(), aiInuse);
	}
}
