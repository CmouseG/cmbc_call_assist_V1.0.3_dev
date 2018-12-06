package com.guiji.ai.tts.service;

import java.util.List;

import com.guiji.ai.vo.TtsGpuReqVO;
import com.guiji.ai.vo.TtsGpuVO;
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
	 * 分页
	 * @return
	 */
	public List<TtsGpuVO> getAllGpuByPage(TtsGpuReqVO ttsGpuReqVO);
}
