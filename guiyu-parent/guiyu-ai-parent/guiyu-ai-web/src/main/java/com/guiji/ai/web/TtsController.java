package com.guiji.ai.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.ai.api.ITts;
import com.guiji.ai.tts.TtsReqQueue;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;

/**
 * 语音合成 Created by ty on 2018/11/13.
 */
@RestController
public class TtsController implements ITts {
	private static Logger logger = LoggerFactory.getLogger(TtsController.class);

	
	@Override
	@PostMapping(value = "translate")
	public ReturnData<String> translate(@RequestBody TtsReqVO ttsReqVO) {

		try {
			if (ttsReqVO != null && !ttsReqVO.getContents().isEmpty()) 
			{
				TtsReqQueue.getInstance().produce(ttsReqVO);
			}

		} catch (Exception e) {
			logger.error("", e);
		}
		return Result.ok("success");
	}
}
