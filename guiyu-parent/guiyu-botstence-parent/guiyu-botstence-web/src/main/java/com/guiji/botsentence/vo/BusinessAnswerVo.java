package com.guiji.botsentence.vo;

import com.guiji.botsentence.dao.entity.VoliceInfo;

import java.util.List;

public class BusinessAnswerVo {
	//流程id
	private String processId;
	//用户问题
	private String userAsk;
	//文案
	private String content;
	//关键字
	private String keyWords;
	//意图id
	private String intentId;
	//音频id
	private String voliceId;
	
	private String templateId;
	
	private String branchId;
	
	private String next;
	
	private String nextKeyword;
	
	private String needAgent;
	
	private String weight;
	
	private String rule;
	
	private String end;
	
	private List<BotSentenceIntentVO> intentList;

	private List<VoliceInfo> voiceInfoList;
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getUserAsk() {
		return userAsk;
	}

	public void setUserAsk(String userAsk) {
		this.userAsk = userAsk;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getIntentId() {
		return intentId;
	}

	public void setIntentId(String intentId) {
		this.intentId = intentId;
	}

	public String getVoliceId() {
		return voliceId;
	}

	public void setVoliceId(String voliceId) {
		this.voliceId = voliceId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getNextKeyword() {
		return nextKeyword;
	}

	public void setNextKeyword(String nextKeyword) {
		this.nextKeyword = nextKeyword;
	}

	public String getNeedAgent() {
		return needAgent;
	}

	public void setNeedAgent(String needAgent) {
		this.needAgent = needAgent;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public List<BotSentenceIntentVO> getIntentList() {
		return intentList;
	}

	public void setIntentList(List<BotSentenceIntentVO> intentList) {
		this.intentList = intentList;
	}

	public List<VoliceInfo> getVoiceInfoList() {
		return voiceInfoList;
	}

	public void setVoiceInfoList(List<VoliceInfo> voiceInfoList) {
		this.voiceInfoList = voiceInfoList;
	}
}