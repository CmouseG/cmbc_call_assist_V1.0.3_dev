package com.guiji.da.service.vo;

import java.util.Map;

/** 
* @ClassName: RobotCallProcessStatCache 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @date 2018年12月6日 下午4:33:47 
* @version V1.0  
*/
public class RobotCallProcessStatCache {
	//用户编号
	private String userId;
	//机构号
	private String orgCode;
	//统计日期
    private String statDate;
    //话术模板
    private String templateId;
    //AI话术
    private String aiAnswer;
    //当前域
    private String currentDomain;
    //域类型 1-主流程;2-一般问题;9-其他
    private String domainType;
    //总数统计
    private int totalStat;
    //拒绝统计map
    private Map<String,Integer> refusedStatMap;
    //挂断统计map
    private Map<String,Integer> hangupStatMap;
    //匹配统计map
    private Map<String,Integer> matchStatMap;
    //domain+aiAnswer的合并（用来做分组统计使用）
    private String domainandaianswer;
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the statDate
	 */
	public String getStatDate() {
		return statDate;
	}
	/**
	 * @param statDate the statDate to set
	 */
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}
	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	/**
	 * @return the aiAnswer
	 */
	public String getAiAnswer() {
		return aiAnswer;
	}
	/**
	 * @param aiAnswer the aiAnswer to set
	 */
	public void setAiAnswer(String aiAnswer) {
		this.aiAnswer = aiAnswer;
	}
	/**
	 * @return the currentDomain
	 */
	public String getCurrentDomain() {
		return currentDomain;
	}
	/**
	 * @param currentDomain the currentDomain to set
	 */
	public void setCurrentDomain(String currentDomain) {
		this.currentDomain = currentDomain;
	}
	/**
	 * @return the domainType
	 */
	public String getDomainType() {
		return domainType;
	}
	/**
	 * @param domainType the domainType to set
	 */
	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}
	/**
	 * @return the totalStat
	 */
	public int getTotalStat() {
		return totalStat;
	}
	/**
	 * @param totalStat the totalStat to set
	 */
	public void setTotalStat(int totalStat) {
		this.totalStat = totalStat;
	}
	/**
	 * @return the refusedStatMap
	 */
	public Map<String, Integer> getRefusedStatMap() {
		return refusedStatMap;
	}
	/**
	 * @param refusedStatMap the refusedStatMap to set
	 */
	public void setRefusedStatMap(Map<String, Integer> refusedStatMap) {
		this.refusedStatMap = refusedStatMap;
	}
	/**
	 * @return the hangupStatMap
	 */
	public Map<String, Integer> getHangupStatMap() {
		return hangupStatMap;
	}
	/**
	 * @param hangupStatMap the hangupStatMap to set
	 */
	public void setHangupStatMap(Map<String, Integer> hangupStatMap) {
		this.hangupStatMap = hangupStatMap;
	}
	/**
	 * @return the matchStatMap
	 */
	public Map<String, Integer> getMatchStatMap() {
		return matchStatMap;
	}
	/**
	 * @param matchStatMap the matchStatMap to set
	 */
	public void setMatchStatMap(Map<String, Integer> matchStatMap) {
		this.matchStatMap = matchStatMap;
	}
	/**
	 * @return the domainandaianswer
	 */
	public String getDomainandaianswer() {
		return this.currentDomain+this.aiAnswer;
	}
	/**
	 * @param domainandaianswer the domainandaianswer to set
	 */
	public void setDomainandaianswer(String domainandaianswer) {
		this.domainandaianswer = domainandaianswer;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RobotCallProcessStatCache [userId=" + userId + ", orgCode=" + orgCode + ", statDate=" + statDate
				+ ", templateId=" + templateId + ", aiAnswer=" + aiAnswer + ", currentDomain=" + currentDomain
				+ ", domainType=" + domainType + ", totalStat=" + totalStat + ", refusedStatMap=" + refusedStatMap
				+ ", hangupStatMap=" + hangupStatMap + ", matchStatMap=" + matchStatMap + ", domainandaianswer="
				+ domainandaianswer + "]";
	}
	
}
