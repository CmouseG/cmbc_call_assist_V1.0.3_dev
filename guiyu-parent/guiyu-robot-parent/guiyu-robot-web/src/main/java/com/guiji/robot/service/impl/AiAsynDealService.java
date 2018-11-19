package com.guiji.robot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.robot.dao.entity.AiCycleHis;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.service.IAiCycleHisService;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.BeanUtil;

/** 
* @ClassName: AiAsynDealService 
* @Description: 系统异步处理服务--因异步不能再同一个service中处理，所以单拉个服务只处理异步逻辑
* @date 2018年11月19日 下午3:44:00 
* @version V1.0  
*/
@Service
public class AiAsynDealService {
	@Autowired
	IAiCycleHisService iAiCycleHisService;
	
	/**
	 * 异步记录用户账户配置信息变更
	 * @param userAiCfgInfo
	 */
	@Transactional
	@Async
	public void recordUserAiCfg(UserAiCfgInfo userAiCfgInfo) {
		
	}
	
	/**
	 * 初始化机器人日志
	 * @param list
	 */
	@Transactional
	@Async
	public void initAiCycleHis(List<AiInuseCache> list) {
		if(ListUtil.isNotEmpty(list)) {
			for(AiInuseCache ai : list) {
				AiCycleHis aiCycleHis = new AiCycleHis();
				BeanUtil.copyProperties(ai, aiCycleHis);
				
				iAiCycleHisService.saveOrUpdate(aiCycleHis);
			}
		}
	}
}
