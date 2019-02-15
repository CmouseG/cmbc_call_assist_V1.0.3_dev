package com.guiji.ai.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.ai.api.IAi;
import com.guiji.ai.service.IAiService;
import com.guiji.ai.vo.AsynPostReqVO;
import com.guiji.ai.vo.SynPostReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;

/**
 * 语音合成
 */
@RestController
public class AiController implements IAi
{
	private static Logger logger = LoggerFactory.getLogger(AiController.class);

	@Autowired
	IAiService ai;
	
	@Override
	@PostMapping(value = "synPost")
	public ReturnData<File> synPost(@RequestBody SynPostReqVO postVO) {
		try
		{
			String model = postVO.getModel();
			File file = ai.getPlat(model).synPost(postVO);
			return Result.ok(file);
		} catch (Exception e) {
			logger.error("请求失败！", e);
			return null;
		}
	}

	@Override
	@PostMapping(value = "asynPost")
	public ReturnData<String> asynPost(@RequestBody AsynPostReqVO postVO)
	{
		try
		{
			String model = postVO.getModel();
			String result = ai.getPlat(model).asynPost(postVO);
			return Result.ok(result);
		} catch (Exception e) {
			logger.error("请求失败！", e);
			return null;
		}
	}
	
	@Override
	@PostMapping(value = "callback")
	public ReturnData<TtsRspVO> callback(@RequestBody TtsRspVO ttsRsp)
	{
		logger.info(ttsRsp.toString());
		return Result.ok(ttsRsp);
	}

	@Override
	@GetMapping(value = "getResult")
	public ReturnData<TtsRspVO> getResult(String busId)
	{
		return Result.ok(ai.getResultByBusId(busId));
	}
	
}
