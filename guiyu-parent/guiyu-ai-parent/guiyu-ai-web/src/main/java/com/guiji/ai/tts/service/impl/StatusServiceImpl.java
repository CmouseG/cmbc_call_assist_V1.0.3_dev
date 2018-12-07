package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.ai.dao.TtsStatusMapper;
import com.guiji.ai.dao.entity.TtsStatus;
import com.guiji.ai.dao.entity.TtsStatusExample;
import com.guiji.ai.dao.entity.TtsStatusExample.Criteria;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.constants.GuiyuAIExceptionEnum;
import com.guiji.ai.tts.handler.SaveTtsStatusHandler;
import com.guiji.ai.tts.service.IStatusService;
import com.guiji.ai.vo.TaskListReqVO;
import com.guiji.ai.vo.TaskListRspVO;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsStatusReqVO;
import com.guiji.ai.vo.TtsStatusRspVO;
import com.guiji.common.exception.GuiyuException;

@Service
public class StatusServiceImpl implements IStatusService
{
	@Autowired
	TtsStatusMapper ttsStatusMapper;

	@Override
	@Transactional
	public String getTransferStatusByBusId(String busId)
	{
		String status = "";
		try
		{
			status = ttsStatusMapper.getReqStatusByBusId(busId);
		} catch (Exception e)
		{
			throw new GuiyuException(GuiyuAIExceptionEnum.EXCP_AI_CHANGE_TTS.getErrorCode(), e);
		}
		return status;

	}

	
	@Override
	@Transactional
	public List<TtsStatusRspVO> getTtsStatusList(TtsStatusReqVO ttsStatusReqVO)
	{
		//结果集
		List<TtsStatusRspVO> ttsStatusRspList = new ArrayList<>();
		
		TtsStatusExample example = new TtsStatusExample();
		example.createCriteria().andModelEqualTo(ttsStatusReqVO.getModel())
								.andStatusEqualTo(ttsStatusReqVO.getStatus())
								.andCreateTimeGreaterThanOrEqualTo(ttsStatusReqVO.getStartTime())
								.andCreateTimeLessThanOrEqualTo(ttsStatusReqVO.getEndTime());
		
		List<TtsStatus> ttsStatusList = ttsStatusMapper.selectByExample(example);
		
		for(TtsStatus ttsStatus : ttsStatusList)
		{
			TtsStatusRspVO ttsStatusRsp = new TtsStatusRspVO();
			ttsStatusRsp.setBusId(ttsStatus.getBusId());
			ttsStatusRsp.setModel(ttsStatus.getModel());
			ttsStatusRsp.setCount(ttsStatus.getTextCount());
			ttsStatusRsp.setCreateTime(ttsStatus.getCreateTime());
			ttsStatusRspList.add(ttsStatusRsp);
		}
		
		return ttsStatusRspList;
	}

	
	/**
	 * ttsReqVO入库
	 */
	@Override
	public void saveTtsStatus(TtsReqVO ttsReqVO)
	{
		TtsStatus ttsStatus = new TtsStatus();
		ttsStatus.setBusId(ttsReqVO.getBusId());
		ttsStatus.setModel(ttsReqVO.getModel());
		ttsStatus.setStatus(AiConstants.UNTREATED); //未处理
		ttsStatus.setCreateTime(new Date());
		ttsStatus.setTextCount(ttsReqVO.getContents().size());
		SaveTtsStatusHandler.getInstance().add(ttsStatus, ttsStatusMapper);
		
	}

	@Override
	@Transactional
	public void updateStatusByBusId(String busId, String status)
	{
		ttsStatusMapper.updateStatusByBusId(busId, status);
		
	}

	@Override
	@Transactional
	public TaskListRspVO getTaskList(TaskListReqVO taskListReqVO)
	{
		TaskListRspVO taskListRspVO = new TaskListRspVO();
		
		List<TtsStatus> ttsStatusList = new ArrayList<>();
		TtsStatusExample TtsStatusExample = new TtsStatusExample();
		
		if(taskListReqVO == null)
		{
			ttsStatusList = ttsStatusMapper.selectByExample(TtsStatusExample);
		}
		else
		{
			Criteria criteria = TtsStatusExample.createCriteria();
			if(taskListReqVO.getBusId() != null){
				criteria.andBusIdEqualTo(taskListReqVO.getBusId());
			}
			if(taskListReqVO.getModel() != null){
				criteria.andModelEqualTo(taskListReqVO.getModel());
			}
			if(taskListReqVO.getStatus() != null){
				criteria.andStatusEqualTo(taskListReqVO.getStatus());
			}
			if(taskListReqVO.getStartTime() != null){
				criteria.andCreateTimeGreaterThanOrEqualTo(taskListReqVO.getStartTime());
			}
			if(taskListReqVO.getEndTime() != null){
				criteria.andCreateTimeLessThanOrEqualTo(taskListReqVO.getEndTime());
			}
			ttsStatusList = ttsStatusMapper.selectByExample(TtsStatusExample);
		}

		taskListRspVO.setTotalNum(ttsStatusList.size());
		taskListRspVO.setTtsStatusList(ttsStatusList);
		
		return taskListRspVO;
				
	}

}
