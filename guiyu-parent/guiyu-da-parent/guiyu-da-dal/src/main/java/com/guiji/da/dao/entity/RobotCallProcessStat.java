package com.guiji.da.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class RobotCallProcessStat implements Serializable {
    private Integer id;

    private String userId;

    private String statDate;

    private String templateId;

    private String aiAnswer;

    private String currentDomain;

    private String domainType;

    private Integer totalStat;

    private String refusedStat;

    private String hangupStat;

    private String matchStat;

    private String orgCode;

    private Date crtTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getStatDate() {
        return statDate;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate == null ? null : statDate.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getAiAnswer() {
        return aiAnswer;
    }

    public void setAiAnswer(String aiAnswer) {
        this.aiAnswer = aiAnswer == null ? null : aiAnswer.trim();
    }

    public String getCurrentDomain() {
        return currentDomain;
    }

    public void setCurrentDomain(String currentDomain) {
        this.currentDomain = currentDomain == null ? null : currentDomain.trim();
    }

    public String getDomainType() {
        return domainType;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType == null ? null : domainType.trim();
    }

    public Integer getTotalStat() {
        return totalStat;
    }

    public void setTotalStat(Integer totalStat) {
        this.totalStat = totalStat;
    }

    public String getRefusedStat() {
        return refusedStat;
    }

    public void setRefusedStat(String refusedStat) {
        this.refusedStat = refusedStat == null ? null : refusedStat.trim();
    }

    public String getHangupStat() {
        return hangupStat;
    }

    public void setHangupStat(String hangupStat) {
        this.hangupStat = hangupStat == null ? null : hangupStat.trim();
    }

    public String getMatchStat() {
        return matchStat;
    }

    public void setMatchStat(String matchStat) {
        this.matchStat = matchStat == null ? null : matchStat.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", statDate=").append(statDate);
        sb.append(", templateId=").append(templateId);
        sb.append(", aiAnswer=").append(aiAnswer);
        sb.append(", currentDomain=").append(currentDomain);
        sb.append(", domainType=").append(domainType);
        sb.append(", totalStat=").append(totalStat);
        sb.append(", refusedStat=").append(refusedStat);
        sb.append(", hangupStat=").append(hangupStat);
        sb.append(", matchStat=").append(matchStat);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}