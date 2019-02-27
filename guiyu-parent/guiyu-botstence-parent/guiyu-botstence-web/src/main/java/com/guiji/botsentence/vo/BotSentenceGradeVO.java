package com.guiji.botsentence.vo;

import java.util.Date;

public class BotSentenceGradeVO {

	private String intentName;
	private String ruleNo;
	private String evaluate;
	private Date crtTime;
	private String remark;
	private String initStat;
	
	public String getIntentName() {
		return intentName;
	}
	public void setIntentName(String intentName) {
		this.intentName = intentName;
	}
	public String getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInitStat() {
		return initStat;
	}
	public void setInitStat(String initStat) {
		this.initStat = initStat;
	}

	
}
