package com.guiji.ai.service;

import com.guiji.ai.tts.TtsService;
import com.guiji.ai.vo.TtsRspVO;

public interface IAiService
{
	/**
	 * 根据模型获取tts平台
	 */
	public TtsService getPlat(String model);
	
	/**
	 * 查询异步合成结果
	 */
	public TtsRspVO getResultByBusId(String busId);
}
