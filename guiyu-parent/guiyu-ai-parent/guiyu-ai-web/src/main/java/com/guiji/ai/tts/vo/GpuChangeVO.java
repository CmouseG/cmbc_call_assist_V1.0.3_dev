package com.guiji.ai.tts.vo;

public class GpuChangeVO {

	private String fromModel;
	private String toModel;
	private String ip;
	private String port;
	
	public GpuChangeVO(String fromModel, String toModel, String ip, String port) {
		super();
		this.fromModel = fromModel;
		this.toModel = toModel;
		this.ip = ip;
		this.port = port;
	}

	public String getFromModel() {
		return fromModel;
	}

	public void setFromModel(String fromModel) {
		this.fromModel = fromModel;
	}

	public String getToModel() {
		return toModel;
	}

	public void setToModel(String toModel) {
		this.toModel = toModel;
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
	
}
