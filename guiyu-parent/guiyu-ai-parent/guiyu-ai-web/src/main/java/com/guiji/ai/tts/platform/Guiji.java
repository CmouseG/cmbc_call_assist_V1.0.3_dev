package com.guiji.ai.tts.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.guiji.ai.tts.TtsService;
import com.guiji.ai.util.HttpClientUtil;
import com.guiji.ai.vo.AsynPostReqVO;
import com.guiji.ai.vo.SynPostReqVO;

public class Guiji implements TtsService
{
	private static Logger logger = LoggerFactory.getLogger(Guiji.class);
			
	private String ttsUrl;
	
	public Guiji(String ttsUrl)
	{
		this.ttsUrl = ttsUrl;
	}

	/**
	 * 同步请求
	 */
	@Override
	public String synPost(SynPostReqVO postVO) throws Exception
	{
		logger.info("（同步）请求TTS..." + postVO.toString());
		String result = HttpClientUtil.post(ttsUrl+"synPost", postVO);
		logger.info("（同步）TTS返回结果：" + result);
		return JSONObject.parseObject(result).getString("body");
	}

	/**
	 * 异步请求
	 */
	@Override
	public String asynPost(AsynPostReqVO ttsReq) throws Exception
	{
		logger.info("（异步）请求TTS..." + ttsReq.toString());
		String result = HttpClientUtil.post(ttsUrl+"asynPost", ttsReq);
		logger.info("（异步）TTS返回异步结果：" + result);
		return JSONObject.parseObject(result).getString("body");
	}
}
