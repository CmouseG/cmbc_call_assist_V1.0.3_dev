package com.guiji.ai.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.TtsCallback;

/**
 * 语音合成
 */
@RestController
public class AiController implements IAi
{
	private static Logger logger = LoggerFactory.getLogger(AiController.class);

	@Value("${notifyUrl}")
	private String notifyUrl;
	@Autowired
	IAiService ai;
	@Autowired
	IRobotRemote robotRemote;
	
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
			postVO.setNotifyUrl(notifyUrl);
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
	public void callback(@RequestBody TtsRspVO ttsRsp)
	{
		logger.info("tts回调结果：" + ttsRsp.toString());
		// 回调机器人接口，返回数据
		TtsCallback ttsCallback = new TtsCallback();
		ttsCallback.setBusiId(ttsRsp.getBusId());
		ttsCallback.setStatus(ttsRsp.getStatus());
		ttsCallback.setErrorMsg(ttsRsp.getStatusMsg());
		ttsCallback.setAudios(ttsRsp.getAudios());

		List<TtsCallback> resultList = new ArrayList<TtsCallback>();
		resultList.add(ttsCallback);
		robotRemote.ttsCallback(resultList);
	}

	@Override
	@GetMapping(value = "getResult")
	public ReturnData<TtsRspVO> getResult(String busId)
	{
		return Result.ok(ai.getResultByBusId(busId));
	}
	
}
