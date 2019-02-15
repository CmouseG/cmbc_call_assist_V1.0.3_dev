package com.guiji.ai.tts.platform;

import com.guiji.ai.tts.TtsService;
import com.guiji.ai.util.HttpClientUtil;
import com.guiji.ai.vo.AsynPostReqVO;
import com.guiji.ai.vo.SynPostReqVO;

public class Guiji implements TtsService
{
	private String ttsUrl;
	
	public Guiji(String ttsUrl, String filePath)
	{
		this.ttsUrl = ttsUrl;
	}

	/**
	 * 同步请求
	 */
	@Override
	public String synPost(SynPostReqVO postVO) throws Exception
	{
		String result = HttpClientUtil.post(ttsUrl+"synPost", postVO);
		return result;
	}

	/**
	 * 异步请求
	 */
	@Override
	public String asynPost(AsynPostReqVO ttsReq) throws Exception
	{
		String result = HttpClientUtil.post(ttsUrl+"asynPost", ttsReq);
		return result;
	}
}
