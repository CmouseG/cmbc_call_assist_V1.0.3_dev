package com.guiji.ai.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="GPU模型",description="GPU模型")
public class TtsGpuVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="GPU ip")
	private String ip;
	@ApiModelProperty(value="GPU port")
	private String port;
	@ApiModelProperty(value="模型名称")
	private String model;
	
	
	public TtsGpuVO() {
		super();
	}

	public TtsGpuVO(String ip, String port, String model) {
		super();
		this.ip = ip;
		this.port = port;
		this.model = model;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
}
