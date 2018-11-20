package com.guiji.robot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.guiji.robot.dao.entity.AiCycleHis;
import com.guiji.robot.service.IAiCycleHisService;

/** 
* @ClassName: AiCycleHisServiceImpl 
* @Description: 记录机器人生命周期状态变更历史
* @date 2018年11月15日 下午8:26:02 
* @version V1.0  
*/
@Service
public class AiCycleHisServiceImpl implements IAiCycleHisService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 记录机器人生命周期状态变更历史
	 * @param aiCycleHis
	 * @return
	 */
	@Override
	public AiCycleHis saveOrUpdate(AiCycleHis aiCycleHis) {
		return null;
	}
	

	/**
	 * 根据临时分配id，查询该机器人分配记录
	 * @param assignId
	 * @return
	 */
	@Override
	public AiCycleHis queryAiCycleHisByAssignId(String assignId) {
		return null;
	}
}
