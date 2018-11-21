package com.guiji.robot.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.guiji.utils.DateUtil;

/** 
* @ClassName: AiAsynDealService 
* @Description: 系统异步处理服务--因异步不能再同一个service中处理，所以单拉个服务只处理异步逻辑
* @date 2018年11月19日 下午3:44:00 
* @version V1.0  
*/
@Service
public class AiAsynDealService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
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
				aiCycleHis.setAssignDate(ai.getInitDate());
				aiCycleHis.setAssignTime(ai.getInitTime());
				iAiCycleHisService.saveOrUpdate(aiCycleHis);
			}
		}
	}
	
	
	/**
	 * 释放机器人日志
	 * @param list
	 */
	@Transactional
	@Async
	public void releaseAiCycleHis(List<AiInuseCache> list) {
		if(ListUtil.isNotEmpty(list)) {
			for(AiInuseCache ai : list) {
				//先查找分配历史
				List<AiCycleHis> assignList = iAiCycleHisService.queryByUserIdAndAiNo(ai.getUserId(), ai.getAiNo());
				if(ListUtil.isNotEmpty(assignList)) {
					AiCycleHis his = assignList.get(0);
					his.setCallNum(Long.valueOf(ai.getCallNum()));	//总拨打电话数
					his.setTaskbackDate(DateUtil.getCurrentymd());	//回收日期
					his.setTaskbackTime(DateUtil.getCurrentTime());	//回收时间
					iAiCycleHisService.saveOrUpdate(his);
				}
			}
		}
	}
	
	
}
