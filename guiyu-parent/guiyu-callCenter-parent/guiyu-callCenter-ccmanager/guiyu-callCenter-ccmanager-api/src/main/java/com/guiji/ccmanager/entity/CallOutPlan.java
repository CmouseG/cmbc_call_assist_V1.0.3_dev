package com.guiji.ccmanager.entity;

import java.io.Serializable;
import java.util.Date;

public class CallOutPlan implements Serializable {
    private String callId;

    private String phoneNum;

    private String customerId;

    private String tempId;

    private Integer lineId;

    private String serverid;

    private String agentId;

    private Date agentAnswerTime;

    private String agentChannelUuid;

    private String agentGroupId;

    private Date agentStartTime;

    private Date createTime;

    private Date callStartTime;

    private Date hangupTime;

    private Date answerTime;

    private Integer duration;

    private Integer billSec;

    private Integer callDirection;

    private Integer callState;

    private Integer hangupDirection;

    private String accurateIntent;

    private String reason;

    private String hangupCode;

    private String originateCmd;

    private String remarks;

    private Boolean hasTts;

    private String aiId;

    private Integer freason;

    private static final long serialVersionUID = 1L;

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId == null ? null : callId.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId == null ? null : tempId.trim();
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getServerid() {
        return serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid == null ? null : serverid.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public Date getAgentAnswerTime() {
        return agentAnswerTime;
    }

    public void setAgentAnswerTime(Date agentAnswerTime) {
        this.agentAnswerTime = agentAnswerTime;
    }

    public String getAgentChannelUuid() {
        return agentChannelUuid;
    }

    public void setAgentChannelUuid(String agentChannelUuid) {
        this.agentChannelUuid = agentChannelUuid == null ? null : agentChannelUuid.trim();
    }

    public String getAgentGroupId() {
        return agentGroupId;
    }

    public void setAgentGroupId(String agentGroupId) {
        this.agentGroupId = agentGroupId == null ? null : agentGroupId.trim();
    }

    public Date getAgentStartTime() {
        return agentStartTime;
    }

    public void setAgentStartTime(Date agentStartTime) {
        this.agentStartTime = agentStartTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(Date callStartTime) {
        this.callStartTime = callStartTime;
    }

    public Date getHangupTime() {
        return hangupTime;
    }

    public void setHangupTime(Date hangupTime) {
        this.hangupTime = hangupTime;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getBillSec() {
        return billSec;
    }

    public void setBillSec(Integer billSec) {
        this.billSec = billSec;
    }

    public Integer getCallDirection() {
        return callDirection;
    }

    public void setCallDirection(Integer callDirection) {
        this.callDirection = callDirection;
    }

    public Integer getCallState() {
        return callState;
    }

    public void setCallState(Integer callState) {
        this.callState = callState;
    }

    public Integer getHangupDirection() {
        return hangupDirection;
    }

    public void setHangupDirection(Integer hangupDirection) {
        this.hangupDirection = hangupDirection;
    }

    public String getAccurateIntent() {
        return accurateIntent;
    }

    public void setAccurateIntent(String accurateIntent) {
        this.accurateIntent = accurateIntent == null ? null : accurateIntent.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getHangupCode() {
        return hangupCode;
    }

    public void setHangupCode(String hangupCode) {
        this.hangupCode = hangupCode == null ? null : hangupCode.trim();
    }

    public String getOriginateCmd() {
        return originateCmd;
    }

    public void setOriginateCmd(String originateCmd) {
        this.originateCmd = originateCmd == null ? null : originateCmd.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Boolean getHasTts() {
        return hasTts;
    }

    public void setHasTts(Boolean hasTts) {
        this.hasTts = hasTts;
    }

    public String getAiId() {
        return aiId;
    }

    public void setAiId(String aiId) {
        this.aiId = aiId == null ? null : aiId.trim();
    }

    public Integer getFreason() {
        return freason;
    }

    public void setFreason(Integer freason) {
        this.freason = freason;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", callId=").append(callId);
        sb.append(", phoneNum=").append(phoneNum);
        sb.append(", customerId=").append(customerId);
        sb.append(", tempId=").append(tempId);
        sb.append(", lineId=").append(lineId);
        sb.append(", serverid=").append(serverid);
        sb.append(", agentId=").append(agentId);
        sb.append(", agentAnswerTime=").append(agentAnswerTime);
        sb.append(", agentChannelUuid=").append(agentChannelUuid);
        sb.append(", agentGroupId=").append(agentGroupId);
        sb.append(", agentStartTime=").append(agentStartTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", callStartTime=").append(callStartTime);
        sb.append(", hangupTime=").append(hangupTime);
        sb.append(", answerTime=").append(answerTime);
        sb.append(", duration=").append(duration);
        sb.append(", billSec=").append(billSec);
        sb.append(", callDirection=").append(callDirection);
        sb.append(", callState=").append(callState);
        sb.append(", hangupDirection=").append(hangupDirection);
        sb.append(", accurateIntent=").append(accurateIntent);
        sb.append(", reason=").append(reason);
        sb.append(", hangupCode=").append(hangupCode);
        sb.append(", originateCmd=").append(originateCmd);
        sb.append(", remarks=").append(remarks);
        sb.append(", hasTts=").append(hasTts);
        sb.append(", aiId=").append(aiId);
        sb.append(", freason=").append(freason);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}