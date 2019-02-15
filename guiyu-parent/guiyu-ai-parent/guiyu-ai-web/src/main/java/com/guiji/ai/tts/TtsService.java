package com.guiji.ai.tts;

import com.guiji.ai.vo.AsynPostReqVO;
import com.guiji.ai.vo.SynPostReqVO;

public interface TtsService
{
	/**
	 * 同步请求
	 */
	public String synPost(SynPostReqVO postVO) throws Exception;
	
	/**
	 * 异步请求
	 */
	public String asynPost(AsynPostReqVO ttsReq) throws Exception;
}
