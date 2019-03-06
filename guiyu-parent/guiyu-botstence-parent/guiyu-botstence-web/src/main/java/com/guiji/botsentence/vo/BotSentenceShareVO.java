package com.guiji.botsentence.vo;

import java.util.List;

public class BotSentenceShareVO {

	private String processId;
	private String templateId;
	private String userId;
	private String templateName;
	private String type;
	private List<String> orgCodeList;
	private String userName;
	private String shareUserId;
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getOrgCodeList() {
		return orgCodeList;
	}
	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getShareUserId() {
		return shareUserId;
	}
	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}
	
	
}
