package com.guiji.ai.tts.vo;

import java.io.Serializable;
import java.util.List;

import com.guiji.ai.tts.service.impl.GuiyuTtsGpu;

public class GpuCountVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String model;
	private int requestCount;
	private int availableGpuCount;
	private int changeCount;
	private List<GuiyuTtsGpu> gpuList;
	
	public GpuCountVO(String model, int requestCount, int gpuCount, int changeCount, List<GuiyuTtsGpu> gpuList) {
		super();
		this.model = model;
		this.requestCount = requestCount;
		this.availableGpuCount = gpuCount;
		this.changeCount = changeCount;
		this.gpuList = gpuList;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getRequestCount() {
		return requestCount;
	}
	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}
	public int getAvailableGpuCount() {
		return availableGpuCount;
	}
	public void setAvailableGpuCount(int availableGpuCount) {
		this.availableGpuCount = availableGpuCount;
	}
	public int getChangeCount() {
		return changeCount;
	}
	public void setChangeCount(int changeCount) {
		this.changeCount = changeCount;
	}
	public List<GuiyuTtsGpu> getGpuList() {
		return gpuList;
	}
	public void setGpuList(List<GuiyuTtsGpu> gpuList) {
		this.gpuList = gpuList;
	}
	
}
