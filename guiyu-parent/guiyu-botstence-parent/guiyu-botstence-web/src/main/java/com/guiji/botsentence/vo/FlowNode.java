package com.guiji.botsentence.vo;

import java.util.List;

public class FlowNode {

	private String id;
	private String label;
	private int x;
	private int y;
	private String processId;
	//private String domainName;
	private String content;
	private String contentUrl;
	private String type;
	private String startExplainText;
	private String startExplainKeywords;
	private String startExplainUrl;
	private List<RefuseBranchVO> refuses; 
	private String shape;
	private String startExplainDomainName;
	private List<RefuseBranchVO> startExplainRefuses; 
	private List<BotSentenceIntentVO> startExplainIntentList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	/*public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}*/
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStartExplainText() {
		return startExplainText;
	}
	public void setStartExplainText(String startExplainText) {
		this.startExplainText = startExplainText;
	}
	public String getStartExplainKeywords() {
		return startExplainKeywords;
	}
	public void setStartExplainKeywords(String startExplainKeywords) {
		this.startExplainKeywords = startExplainKeywords;
	}
	public String getStartExplainUrl() {
		return startExplainUrl;
	}
	public void setStartExplainUrl(String startExplainUrl) {
		this.startExplainUrl = startExplainUrl;
	}
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	public List<RefuseBranchVO> getRefuses() {
		return refuses;
	}
	public void setRefuses(List<RefuseBranchVO> refuses) {
		this.refuses = refuses;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	
	public String getStartExplainDomainName() {
		return startExplainDomainName;
	}
	public void setStartExplainDomainName(String startExplainDomainName) {
		this.startExplainDomainName = startExplainDomainName;
	}
	public List<RefuseBranchVO> getStartExplainRefuses() {
		return startExplainRefuses;
	}
	public void setStartExplainRefuses(List<RefuseBranchVO> startExplainRefuses) {
		this.startExplainRefuses = startExplainRefuses;
	}
	public List<BotSentenceIntentVO> getStartExplainIntentList() {
		return startExplainIntentList;
	}
	public void setStartExplainIntentList(List<BotSentenceIntentVO> startExplainIntentList) {
		this.startExplainIntentList = startExplainIntentList;
	}
	
	
}
