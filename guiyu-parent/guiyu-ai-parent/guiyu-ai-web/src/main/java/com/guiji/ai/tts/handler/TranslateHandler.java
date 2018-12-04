package com.guiji.ai.tts.handler;

import com.guiji.ai.dao.TtsStatusMapper;
import com.guiji.ai.tts.TtsReqQueue;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.ITtsService;
import com.guiji.ai.vo.TtsReqVO;

public class TranslateHandler {

	public void run(ITtsService ttsService, TtsStatusMapper ttsStatusMapper) {
		
		TtsReqVO ttsReqVO = null;
		while(true)
		{
			try 
			{
				ttsReqVO = TtsReqQueue.getInstance().get();
				if(ttsReqVO == null)
				{
					continue;
				}
				
				//修改表状态
				ttsStatusMapper.updateStatusByBusId(ttsReqVO.getBusId(), AiConstants.DOING);
				
				//语言合成
				ttsService.translate(ttsReqVO);
				
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}



}
