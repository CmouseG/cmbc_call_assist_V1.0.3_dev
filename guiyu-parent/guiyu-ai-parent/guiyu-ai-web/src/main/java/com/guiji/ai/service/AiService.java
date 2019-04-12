package com.guiji.ai.service;

import com.guiji.ai.bean.AsynPostReq;
import com.guiji.ai.bean.SynPostReq;
import com.guiji.ai.bean.TtsRsp;

public interface AiService
{
	public String synPost(SynPostReq synPostReq);

	public String asynPost(AsynPostReq asynPostReq);

	public TtsRsp getResultByBusId(String busId);
	
}
