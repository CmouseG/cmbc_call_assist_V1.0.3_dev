package com.guiji.ai.tts.vo;

import java.io.Serializable;

public class ModelRequestNumVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String model;
	private int requestNum;
	
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public int getRequestNum()
	{
		return requestNum;
	}
	public void setRequestNum(int requestNum)
	{
		this.requestNum = requestNum;
	}
	
}
