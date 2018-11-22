package com.guiji.ai.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.ai.api.ITts;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.TtsService;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.component.result.Result;

/**
 * 语音合成
 * Created by ty on 2018/11/13.
 */
@RestController
public class TtsController implements ITts {
	private static Logger logger = LoggerFactory.getLogger(TtsController.class);
	
	@Autowired
	TtsService ttsService;
	
    @Override
    @PostMapping(value = "translate")
    public Result.ReturnData<TtsRspVO> translate(TtsReqVO ttsReqVO) {
    	logger.info("开始语音合成!");
    	try {
    		TtsRspVO ttsRspVO = ttsService.translate(ttsReqVO);
    		if(ttsRspVO != null){
    			logger.info("语音合成完毕!");
        		return Result.ok(ttsRspVO);
    		}
    		return null;
		} catch (Exception e) {
			logger.error("语音合成失败!",e);
			return Result.error(AiConstants.AI_TRANSFER_ERROR);
		}
    }
}
