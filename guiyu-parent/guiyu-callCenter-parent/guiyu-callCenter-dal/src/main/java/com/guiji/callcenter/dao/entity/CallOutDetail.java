package com.guiji.callcenter.dao.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class CallOutDetail implements Serializable {
    private BigInteger callDetailId;

    private BigInteger callId;

    private String accurateIntent;

    private String agentAnswerText;

    private Date agentAnswerTime;

    private Integer aiDuration;

    private Integer asrDuration;

    private String botAnswerText;

    private Date botAnswerTime;

    private Integer callDetailType;

    private String customerSayText;

    private Date customerSayTime;

    private String reason;

    private Integer totalDuration;

    private Integer isupdate;

    private String wordSegmentResult;

    private String keywords;

    private Integer orgId;

    private static final long serialVersionUID = 1L;

    public BigInteger getCallDetailId() {
        return callDetailId;
    }

    public void setCallDetailId(BigInteger callDetailId) {
        this.callDetailId = callDetailId;
    }

    public BigInteger getCallId() {
        return callId;
    }

    public void setCallId(BigInteger callId) {
        this.callId = callId;
    }

    public String getAccurateIntent() {
        return accurateIntent;
    }

    public void setAccurateIntent(String accurateIntent) {
        this.accurateIntent = accurateIntent == null ? null : accurateIntent.trim();
    }

    public String getAgentAnswerText() {
        return agentAnswerText;
    }

    public void setAgentAnswerText(String agentAnswerText) {
        this.agentAnswerText = agentAnswerText == null ? null : agentAnswerText.trim();
    }

    public Date getAgentAnswerTime() {
        return agentAnswerTime;
    }

    public void setAgentAnswerTime(Date agentAnswerTime) {
        this.agentAnswerTime = agentAnswerTime;
    }

    public Integer getAiDuration() {
        return aiDuration;
    }

    public void setAiDuration(Integer aiDuration) {
        this.aiDuration = aiDuration;
    }

    public Integer getAsrDuration() {
        return asrDuration;
    }

    public void setAsrDuration(Integer asrDuration) {
        this.asrDuration = asrDuration;
    }

    public String getBotAnswerText() {
        return botAnswerText;
    }

    public void setBotAnswerText(String botAnswerText) {
        this.botAnswerText = botAnswerText == null ? null : botAnswerText.trim();
    }

    public Date getBotAnswerTime() {
        return botAnswerTime;
    }

    public void setBotAnswerTime(Date botAnswerTime) {
        this.botAnswerTime = botAnswerTime;
    }

    public Integer getCallDetailType() {
        return callDetailType;
    }

    public void setCallDetailType(Integer callDetailType) {
        this.callDetailType = callDetailType;
    }

    public String getCustomerSayText() {
        return customerSayText;
    }

    public void setCustomerSayText(String customerSayText) {
        this.customerSayText = customerSayText == null ? null : customerSayText.trim();
    }

    public Date getCustomerSayTime() {
        return customerSayTime;
    }

    public void setCustomerSayTime(Date customerSayTime) {
        this.customerSayTime = customerSayTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Integer getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(Integer isupdate) {
        this.isupdate = isupdate;
    }

    public String getWordSegmentResult() {
        return wordSegmentResult;
    }

    public void setWordSegmentResult(String wordSegmentResult) {
        this.wordSegmentResult = wordSegmentResult == null ? null : wordSegmentResult.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", callDetailId=").append(callDetailId);
        sb.append(", callId=").append(callId);
        sb.append(", accurateIntent=").append(accurateIntent);
        sb.append(", agentAnswerText=").append(agentAnswerText);
        sb.append(", agentAnswerTime=").append(agentAnswerTime);
        sb.append(", aiDuration=").append(aiDuration);
        sb.append(", asrDuration=").append(asrDuration);
        sb.append(", botAnswerText=").append(botAnswerText);
        sb.append(", botAnswerTime=").append(botAnswerTime);
        sb.append(", callDetailType=").append(callDetailType);
        sb.append(", customerSayText=").append(customerSayText);
        sb.append(", customerSayTime=").append(customerSayTime);
        sb.append(", reason=").append(reason);
        sb.append(", totalDuration=").append(totalDuration);
        sb.append(", isupdate=").append(isupdate);
        sb.append(", wordSegmentResult=").append(wordSegmentResult);
        sb.append(", keywords=").append(keywords);
        sb.append(", orgId=").append(orgId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}