package com.guiji.botsentence.vo;

import java.util.List;

public class FlowEdge {

	private String id;
	private String source;
	private String target;
	private String label;
	private String text;
	private String keyWords;
	private String processId;
	private String type;
	private List<BotSentenceIntentVO> intentList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<BotSentenceIntentVO> getIntentList() {
		return intentList;
	}
	public void setIntentList(List<BotSentenceIntentVO> intentList) {
		this.intentList = intentList;
	}
	
}
