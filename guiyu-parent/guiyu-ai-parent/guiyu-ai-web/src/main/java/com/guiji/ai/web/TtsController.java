package com.guiji.ai.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.ai.api.ITts;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.constants.GuiyuAIExceptionEnum;
import com.guiji.ai.tts.service.TtsService;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.common.exception.GuiyuException;
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
    public Result.ReturnData<TtsRspVO> translate(@RequestBody TtsReqVO ttsReqVO) {
    	logger.info("开始语音合成!");
    	try {
    		if(ttsReqVO == null){
    			throw new GuiyuException(GuiyuAIExceptionEnum.EXCP_AI_NO_TTS_ReqVO); 
    		}else if(ttsReqVO.getBusId() == null || ttsReqVO.getBusId() == ""){
    			throw new GuiyuException(GuiyuAIExceptionEnum.EXCP_AI_NO_BUSID);
    		}else if(ttsReqVO.getModel() == null || ttsReqVO.getModel() == ""){
    			throw new GuiyuException(GuiyuAIExceptionEnum.EXCP_AI_NO_MODEL);
    		}else if(ttsReqVO.getContents() == null || ttsReqVO.getContents().isEmpty()){
    			throw new GuiyuException(GuiyuAIExceptionEnum.EXCP_AI_NO_CONTENTS);
    		}
			return Result.ok(ttsService.translate(ttsReqVO));
		} catch (Exception e) {
			logger.error("语音合成失败!", e);
			return Result.error(AiConstants.AI_TRANSFER_ERROR);
		}
    }
}
