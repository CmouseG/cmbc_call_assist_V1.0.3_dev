package com.guiji.botsentence.vo;

import java.util.List;

public class TtsParamVO {

	private String processId;
	private String templateId;
	private List<TtsParam> params;
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public List<TtsParam> getParams() {
		return params;
	}
	public void setParams(List<TtsParam> params) {
		this.params = params;
	}
	
	
	
}
