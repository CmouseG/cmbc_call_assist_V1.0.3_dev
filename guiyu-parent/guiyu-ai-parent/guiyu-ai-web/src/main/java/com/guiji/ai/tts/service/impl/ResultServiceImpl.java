package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.ai.dao.TtsResultMapper;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.IResultService;
import com.guiji.ai.tts.vo.ModelRequestNumVO;

@Service
public class ResultServiceImpl implements IResultService
{
	
	@Autowired
	TtsResultMapper ttsResultMapper;


	@Override
	public List<Map<String, String>> getTtsTransferResultByBusId(String busId) throws Exception
	{
		List<Map<String, String>> restltMapList = new ArrayList<>();
		
		restltMapList = ttsResultMapper.getTtsTransferResult(busId);
		
		return restltMapList;
	}
	
	@Override
	public List<ModelRequestNumVO> selectTenMinutesBefore(Date date)
	{
		//结果集
		List<ModelRequestNumVO> modelRequestList = new ArrayList<>();
		
		List<Map<String, Object>> resultList = ttsResultMapper.selectTenMinutesBefore(new Date()); // <A,3>，<B，5>
		if(resultList == null || resultList.isEmpty()){
			return null;
		}
		for(Map<String, Object> map : resultList){
			ModelRequestNumVO modelRequest = new ModelRequestNumVO();
			modelRequest.setModel((String) map.get(AiConstants.MODEL));
			modelRequest.setRequestNum((int) map.get(AiConstants.COUNT));
			modelRequestList.add(modelRequest);
		}
		
		return modelRequestList;
	}
}
