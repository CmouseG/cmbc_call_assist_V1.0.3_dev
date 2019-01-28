package com.guiji.botsentence.controller.server.vo;

import java.util.List;

public class BotSentenceTemplateIndustryVO {

	 private String industryId;

	 private String industryName;
	 
	 private List<BotSentenceTemplateIndustryVO> childs;

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public List<BotSentenceTemplateIndustryVO> getChilds() {
		return childs;
	}

	public void setChilds(List<BotSentenceTemplateIndustryVO> childs) {
		this.childs = childs;
	}
}
