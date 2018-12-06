package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.ai.dao.TtsResultMapper;
import com.guiji.ai.tts.service.IResultService;

@Service
public class ResultServiceImpl implements IResultService
{
	
	@Autowired
	TtsResultMapper ttsResultMapper;



	@Override
	@Transactional
	public List<Map<String, String>> getTtsTransferResultByBusId(String busId) throws Exception
	{
		List<Map<String, String>> restltMapList = new ArrayList<>();
		
		restltMapList = ttsResultMapper.getTtsTransferResult(busId);
		
		return restltMapList;
	}
}
