package com.guiji.botsentence.dao.entity;

import java.util.List;

public class BusinessAnswerTaskExt {
	//流程id
	private String processId;
	//用户问题
	private String userAsk;
	//文案
	private String content;
	//关键字
	private String keyWords;
	
	private String voliceId;
	
	private String voliceUrl;
	
	private String intentId;
	
	private String branchId;
	
	private String agentIntent;
	
	private String no;
	
	private int index;
	
	private String next;
	
	private String nextKeyword;
	
	private String needAgent;
	
	private String rule;
	
	private String weight;
	
	private String branchName;
	
	private List intentList;
	
	private String end;
	
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

	public String getVoliceId() {
		return voliceId;
	}

	public void setVoliceId(String voliceId) {
		this.voliceId = voliceId;
	}

	public String getVoliceUrl() {
		return voliceUrl;
	}

	public void setVoliceUrl(String voliceUrl) {
		this.voliceUrl = voliceUrl;
	}

	public String getIntentId() {
		return intentId;
	}

	public void setIntentId(String intentId) {
		this.intentId = intentId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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

	public String getAgentIntent() {
		return agentIntent;
	}

	public void setAgentIntent(String agentIntent) {
		this.agentIntent = agentIntent;
	}

	public String getNeedAgent() {
		return needAgent;
	}

	public void setNeedAgent(String needAgent) {
		this.needAgent = needAgent;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public List getIntentList() {
		return intentList;
	}

	public void setIntentList(List intentList) {
		this.intentList = intentList;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	
}