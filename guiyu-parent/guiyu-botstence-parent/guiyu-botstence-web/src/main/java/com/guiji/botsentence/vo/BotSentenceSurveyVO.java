package com.guiji.botsentence.vo;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceSurveyIntent;

public class BotSentenceSurveyVO {

	private String processId;
	private String domain;
	private String type;
	private String surveyId;
	private List<BotSentenceSurveyIntent> surveyIntentList;
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<BotSentenceSurveyIntent> getSurveyIntentList() {
		return surveyIntentList;
	}
	public void setSurveyIntentList(List<BotSentenceSurveyIntent> surveyIntentList) {
		this.surveyIntentList = surveyIntentList;
	}
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	
	
}
