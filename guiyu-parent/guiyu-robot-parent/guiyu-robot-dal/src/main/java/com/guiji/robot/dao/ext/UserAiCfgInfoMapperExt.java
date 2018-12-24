package com.guiji.robot.dao.ext;

import com.guiji.robot.dao.entity.ext.UserAiCfgQuery;

/** 
* @ClassName: UserAiCfgBaseInfoMapperExt 
* @Description: 用户机器人配置扩展服务
* @date 2018年12月17日 上午11:26:54 
* @version V1.0  
*/
public interface UserAiCfgInfoMapperExt {
	
	/**
	 * 查询用户机器人配置总数量
	 * @param condition
	 * @return
	 */
	Integer countUserAi(UserAiCfgQuery condition);
}
