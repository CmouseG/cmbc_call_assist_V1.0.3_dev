package com.guiji.ai.tts.service;

import java.util.List;

import com.guiji.ai.tts.vo.ModelGpuNumVO;
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

	/**
	 * 获取模型GPU数量
	 * @return
	 */
	public List<ModelGpuNumVO> getModelGpus();
	
}
