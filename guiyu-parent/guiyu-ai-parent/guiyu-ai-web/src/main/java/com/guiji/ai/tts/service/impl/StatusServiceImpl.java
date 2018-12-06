package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.ai.dao.TtsStatusMapper;
import com.guiji.ai.dao.entity.TtsStatus;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.constants.GuiyuAIExceptionEnum;
import com.guiji.ai.tts.handler.SaveTtsStatusHandler;
import com.guiji.ai.tts.service.IStatusService;
import com.guiji.ai.vo.TtsReqVO;
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
	public List<Map<String, Object>> getTtsStatus(Date startTime, Date endTime, String model, String status) throws Exception
	{
		List<Map<String, Object>> restltMapList = new ArrayList<>();
		
		restltMapList = ttsStatusMapper.getTtsStatus(startTime, endTime, model, status);
		
		return restltMapList;
	}
	
	/**
	 * ttsReqVO入库
	 */
	@Override
	public void saveTtsStatus(TtsReqVO ttsReqVO) throws Exception
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
}
