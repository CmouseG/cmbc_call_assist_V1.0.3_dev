package com.guiji.botsentence.vo;

import java.util.List;

public class BotSentenceIndustryVO {

	private String value;
	private String label;
	private List<BotSentenceIndustryChildren> children;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<BotSentenceIndustryChildren> getChildren() {
		return children;
	}
	public void setChildren(List<BotSentenceIndustryChildren> children) {
		this.children = children;
	}
	
}
