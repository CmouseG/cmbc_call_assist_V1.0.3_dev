package com.guiji.ai.tts.handler;

import com.guiji.ai.tts.TtsReqQueue;
import com.guiji.ai.tts.service.ITtsService;
import com.guiji.ai.vo.TtsReqVO;

public class TranslateHandler {

	public void run(ITtsService ttsService) {
		
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
				
				ttsService.translate(ttsReqVO);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}



}
