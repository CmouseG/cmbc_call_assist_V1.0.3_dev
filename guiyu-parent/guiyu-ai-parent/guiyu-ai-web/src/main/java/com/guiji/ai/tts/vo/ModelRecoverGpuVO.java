package com.guiji.ai.tts.vo;

import java.io.Serializable;
import java.util.List;

import com.guiji.ai.tts.service.impl.func.TtsGpu;

public class ModelRecoverGpuVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String model;
	private List<TtsGpu> recoverGpuList;

	public ModelRecoverGpuVO(String model, List<TtsGpu> recoverGpuList)
	{
		this.model = model;
		this.recoverGpuList = recoverGpuList;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public List<TtsGpu> getRecoverGpuList()
	{
		return recoverGpuList;
	}

	public void setRecoverGpuList(List<TtsGpu> recoverGpuList)
	{
		this.recoverGpuList = recoverGpuList;
	}
	
}
