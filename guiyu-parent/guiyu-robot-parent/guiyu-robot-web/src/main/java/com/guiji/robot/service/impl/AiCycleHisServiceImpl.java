package com.guiji.robot.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.robot.dao.AiCycleHisMapper;
import com.guiji.robot.dao.TtsWavHisMapper;
import com.guiji.robot.dao.entity.AiCycleHis;
import com.guiji.robot.dao.entity.AiCycleHisExample;
import com.guiji.robot.service.IAiCycleHisService;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: AiCycleHisServiceImpl 
* @Description: 记录机器人生命周期状态变更历史
* @date 2018年11月15日 下午8:26:02 
* @version V1.0  
*/
@Service
public class AiCycleHisServiceImpl implements IAiCycleHisService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	AiCycleHisMapper aiCycleHisMapper;
	@Autowired
	TtsWavHisMapper ttsWavHisMapper;
	
	
	/**
	 * 记录机器人生命周期状态变更历史
	 * @param aiCycleHis
	 * @return
	 */
	@Override
	public AiCycleHis saveOrUpdate(AiCycleHis aiCycleHis) {
		if(aiCycleHis != null) {
			if(StrUtils.isEmpty(aiCycleHis.getId())) {
				//如果主键为空，那么新增一条信息
				aiCycleHis.setCrtTime(new Date());
				aiCycleHisMapper.insert(aiCycleHis);
			}else {
				//主键不为空，更新信息
				aiCycleHisMapper.updateByPrimaryKey(aiCycleHis);
			}
		}
		return aiCycleHis;
	}
	

	/**
	 * 根据id，查询该机器人分配记录
	 * @param assignId
	 * @return
	 */
	@Override
	public AiCycleHis queryById(int id) {
		return aiCycleHisMapper.selectByPrimaryKey(id);
	}
	
	
	/**
	 * 
	 * @param userId
	 * @param aiNo
	 * @return
	 */
	@Override
	public List<AiCycleHis> queryByUserIdAndAiNo(String userId,String aiNo){
		if(StrUtils.isNotEmpty(userId) && StrUtils.isNotEmpty(aiNo)) {
			AiCycleHisExample example = new AiCycleHisExample();
			example.createCriteria().andUserIdEqualTo(userId).andAiNoEqualTo(aiNo);
			return aiCycleHisMapper.selectByExample(example);
		}
		return null;
	}
	
}
