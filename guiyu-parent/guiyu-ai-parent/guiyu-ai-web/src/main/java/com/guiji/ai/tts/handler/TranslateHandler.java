package com.guiji.ai.tts.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guiji.ai.dao.TtsStatusMapper;
import com.guiji.ai.tts.TtsReqVOQueue;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.ITtsService;
import com.guiji.ai.vo.TtsReqVO;

public class TranslateHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(TranslateHandler.class);

	public void run(ITtsService ttsService, TtsStatusMapper ttsStatusMapper) {
		
		TtsReqVO ttsReqVO = null;
		while(true)
		{
			try 
			{
				ttsReqVO = TtsReqVOQueue.getInstance().get();
				if(ttsReqVO == null)
				{
					continue;
				}
				
				//修改表状态
				ttsStatusMapper.updateStatusByBusId(ttsReqVO.getBusId(), AiConstants.DOING);
				
				//语言合成
				ttsService.translate(ttsReqVO);
				
			} catch (Exception e) {
				logger.error("处理异常", e);
				continue;
			}
		}
	}



}
