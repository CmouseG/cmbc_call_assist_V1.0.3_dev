package com.guiji.botsentence.api.entity;

import java.util.List;

public class BotSentenceTemplateTradeVO {
	
	 private String label;

	 private String value;
	 
	 private int level;
	 
	 private List<BotSentenceTemplate> processList;
	 
	 private List<BotSentenceTemplateTradeVO> children;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<BotSentenceTemplateTradeVO> getChildren() {
		return children;
	}

	public void setChildren(List<BotSentenceTemplateTradeVO> children) {
		this.children = children;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<BotSentenceTemplate> getProcessList() {
		return processList;
	}

	public void setProcessList(List<BotSentenceTemplate> processList) {
		this.processList = processList;
	}

	
}
