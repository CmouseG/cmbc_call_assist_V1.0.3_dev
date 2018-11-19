package com.guiji.robot.service;

import java.util.List;

import com.guiji.robot.dao.entity.UserAiCfgInfo;

/** 
* @ClassName: IUserAiCfgService 
* @Description: 用户-机器人配置服务
* @date 2018年11月15日 下午8:24:20 
* @version V1.0  
*/
public interface IUserAiCfgService {
	
	/**
	 * 保存或者更新一条用户-机器人配置信息
	 * 同时记录历史
	 * @param userAiCfgInfo
	 * @return
	 */
	UserAiCfgInfo saveOrUpdate(UserAiCfgInfo userAiCfgInfo);
	
	
	/**
	 * 根据用户编号查询用户-机器人配置信息列表
	 * @return
	 */
	List<UserAiCfgInfo> queryUserAiCfgListByUserId(String userId);
	
	
	/**
	 * 查询用户下可以使用某个话术的机器人列表
	 * @param userId
	 * @param templateId
	 * @return
	 */
	List<UserAiCfgInfo> queryUserAiCfgListByUserIdAndTemplate(String userId,String templateId);
}
