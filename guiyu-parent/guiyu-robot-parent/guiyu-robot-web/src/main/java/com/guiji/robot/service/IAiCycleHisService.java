package com.guiji.robot.service;

import com.guiji.robot.dao.entity.AiCycleHis;

/** 
* @ClassName: IAiCycleHisService 
* @Description: 机器人生命分配周期历史服务
* @date 2018年11月15日 下午8:25:30 
* @version V1.0  
*/
public interface IAiCycleHisService {
	
	/**
	 * 记录机器人生命周期状态变更历史
	 * @param aiCycleHis
	 * @return
	 */
	AiCycleHis saveOrUpdate(AiCycleHis aiCycleHis);
}
