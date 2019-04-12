package com.guiji.ai.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.ai.api.IAi;
import com.guiji.ai.bean.AsynPostReq;
import com.guiji.ai.bean.SynPostReq;
import com.guiji.ai.bean.TtsRsp;
import com.guiji.ai.common.Result;
import com.guiji.ai.common.ReturnData;
import com.guiji.ai.service.AiService;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.TtsCallback;

@RestController
public class AiController implements IAi
{
	private static final Logger log = LoggerFactory.getLogger(AiController.class);
	
	@Autowired
	AiService ai;
	@Autowired
	IRobotRemote robotRemote;

	/**
     * 语音合成（同步）
     */
	@PostMapping(value = "synPost")
	public ReturnData<String> synPost(@RequestBody SynPostReq synPostReq) throws Exception
	{
		String audioUrl = ai.synPost(synPostReq);
		return Result.success(audioUrl);
	}

	/**
     * 语音合成（异步）
     */
	@PostMapping(value = "asynPost")
	public ReturnData<String> asynPost(@RequestBody AsynPostReq asynPostReq) throws Exception
	{
		String result = ai.asynPost(asynPostReq);
		return Result.success(result);
	}

	/**
	 * TTS结果回调
	 */
	@PostMapping(value = "callback")
	public void callback(@RequestBody TtsRsp ttsRsp)
	{
		log.info("tts回调结果：" + ttsRsp.toString());
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

	/**
	 * 查询异步合成结果
	 */
	@GetMapping(value = "getResultByBusId")
	public ReturnData<TtsRsp> getResultByBusId(String busId)
	{
		return Result.success(ai.getResultByBusId(busId));
	}

}
