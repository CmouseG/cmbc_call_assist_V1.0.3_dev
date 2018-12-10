package com.guiji.ai.tts.vo;

import java.io.Serializable;

public class ModelGpuNumVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String model;
	private int gpuNums;
	
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public int getGpuNums()
	{
		return gpuNums;
	}
	public void setGpuNums(int gpuNums)
	{
		this.gpuNums = gpuNums;
	}
	
}
