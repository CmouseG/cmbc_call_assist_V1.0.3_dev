package com.guiji.ai.tts.service;

import com.guiji.ai.vo.TtsGpuReqVO;
import com.guiji.ai.vo.TtsGpuRspVO;
import com.guiji.common.model.process.ProcessInstanceVO;

public interface IModelService
{
	/**
	 * 保存到tts_model表
	 * @param processInstance
	 */
	public void saveModel(ProcessInstanceVO processInstance);

	/**
	 * 获取GPU模型列表
	 * @return
	 */
	public TtsGpuRspVO getGpuList(TtsGpuReqVO ttsGpuReqVO);
	
}
