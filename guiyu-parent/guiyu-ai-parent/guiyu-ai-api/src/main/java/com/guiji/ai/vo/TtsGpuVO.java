package com.guiji.ai.vo;

import java.io.Serializable;

public class TtsGpuVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String ip;
	private String port;
	private String model;
	
	public String getIp()
	{
		return ip;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	public String getPort()
	{
		return port;
	}
	public void setPort(String port)
	{
		this.port = port;
	}
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	
	public TtsGpuVO()
	{}
	
	public TtsGpuVO(String ip, String port, String model)
	{
		this.ip = ip;
		this.port = port;
		this.model = model;
	}
	

	
}
