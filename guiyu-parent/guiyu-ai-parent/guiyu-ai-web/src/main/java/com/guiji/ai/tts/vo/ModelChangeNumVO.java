package com.guiji.ai.tts.vo;

import java.io.Serializable;

public class ModelChangeNumVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String model;
	private int changeNum;
	
	public ModelChangeNumVO(String model, int changeNum)
	{
		this.model = model;
		this.changeNum = changeNum;
	}
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public int getChangeNum()
	{
		return changeNum;
	}
	public void setChangeNum(int changeNum)
	{
		this.changeNum = changeNum;
	}
	
}
