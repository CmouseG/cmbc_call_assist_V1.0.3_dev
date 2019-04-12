package com.guiji.ai.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guiji.ai.bean.AsynPostReq;
import com.guiji.ai.bean.SynPostReq;
import com.guiji.ai.bean.TtsRsp;
import com.guiji.ai.service.AiService;
import com.guiji.ai.utils.HttpClientUtil;
import com.guiji.ai.utils.JsonUtil;

@Service
public class AiServiceimpl implements AiService
{
	private static final Logger log = LoggerFactory.getLogger(AiServiceimpl.class);
	
	@Value("${guiyu_tts_2_url}")
	private String guiyu_tts_2_url;
	@Value("${notifyAiUrl}")
	private String notifyAiUrl;

	/**
	 * 同步请求
	 */
	@Override
	public String synPost(SynPostReq synPostReq)
	{
		log.info("（同步）请求TTS..." + synPostReq.toString());
		String result = HttpClientUtil.post(guiyu_tts_2_url+"synPost", synPostReq);
		log.info("（同步）TTS返回结果：" + result);
		return JSONObject.parseObject(result).getString("data");
	}

	/**
	 * 异步请求
	 */
	@Override
	public String asynPost(AsynPostReq asynPostReq)
	{
		asynPostReq.setNotifyUrl(notifyAiUrl);
		log.info("（异步）请求TTS..." + asynPostReq.toString());
		String result = HttpClientUtil.post(guiyu_tts_2_url+"asynPost", asynPostReq);
		log.info("（异步）TTS返回结果：" + result);
		return JSONObject.parseObject(result).getString("data");
	}

	/**
	 * 查询异步合成结果
	 */
	@Override
	public TtsRsp getResultByBusId(String busId)
	{
		String result = HttpClientUtil.get(guiyu_tts_2_url+"getResultByBusId?busId="+busId);
		JSONObject returnData = JSONObject.parseObject(result);
		return JsonUtil.json2Bean(returnData.getString("data"), TtsRsp.class);
	}
	
}
