package com.guiji.ai.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guiji.ai.entity.AiConstants;
import com.guiji.ai.service.IAiService;
import com.guiji.ai.tts.TtsService;
import com.guiji.ai.tts.platform.Guiji;
import com.guiji.ai.util.HttpClientUtil;
import com.guiji.ai.util.JsonUtil;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.common.exception.GuiyuException;
import com.guiji.utils.RedisUtil;

@Service
public class AiServiceImpl implements IAiService
{
	private static Logger logger = LoggerFactory.getLogger(AiServiceImpl.class);
	
	@Value("${ttsUrl}")
	private String ttsUrl;
	
	@Autowired
	RedisUtil redisUtil;
	
	/**
	 * 根据模型或者tts平台
	 */
	@Override
	public TtsService getPlat(String model)
	{
		List<String> factorys = (List<String>) redisUtil.get(model);
		if(factorys == null || factorys.isEmpty())
		{
			logger.error("该模型没有对应厂商!");
			throw new GuiyuException("该模型没有对应厂商");
		}
		if(factorys.contains(AiConstants.Guiji)){
			return new Guiji(ttsUrl);
		}
		return null;
		
	}

	/**
	 * 查询异步合成结果
	 */
	@Override
	public TtsRspVO getResultByBusId(String busId)
	{
		String result = HttpClientUtil.get(ttsUrl+"getResultByBusId?busId="+busId);
		JSONObject returnData = JSONObject.parseObject(result);
		return JsonUtil.json2Bean(returnData.getString("body"), TtsRspVO.class);
	}

}
