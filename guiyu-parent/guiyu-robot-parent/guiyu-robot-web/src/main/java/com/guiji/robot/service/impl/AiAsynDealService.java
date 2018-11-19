package com.guiji.robot.service.impl;

import com.guiji.robot.dao.entity.UserAiCfgInfo;

/** 
* @ClassName: AiAsynDealService 
* @Description: 系统异步处理服务--因异步不能再同一个service中处理，所以单拉个服务只处理异步逻辑
* @date 2018年11月19日 下午3:44:00 
* @version V1.0  
*/
public interface AiAsynDealService {

	/**
	 * 异步记录用户账户配置信息变更
	 * @param userAiCfgInfo
	 */
	public void recordUserAiCfg(UserAiCfgInfo userAiCfgInfo);
	
	
	
}
