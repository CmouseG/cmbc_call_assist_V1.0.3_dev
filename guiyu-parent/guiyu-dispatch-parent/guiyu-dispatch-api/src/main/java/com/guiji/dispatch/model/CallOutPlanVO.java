package com.guiji.dispatch.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(description= "通话记录")
public class CallOutPlanVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String callId;
    private String phoneNum;
    private String customerId;
    private String tempId;
    private Integer lineId;
    private Date callStartTime;

    private String serverid;
    private String agentId;
    private Date agentAnswerTime;
    private String agentChannelUuid;
    private String agentGroupId;
    private Date agentStartTime;
    private Date createTime;
    private Date scheduleTime;
    private Date hangupTime;
    private Date answerTime;
    private Integer duration;
    private Integer billSec;
    private Integer callDirection;
    private Integer callState;
    private Integer hangupDirection;
    private String accurateIntent​;
    private String reason;
    private String hangupCode;
    private String originateCmd;
    private String remarks;
    private Integer freason;

    private List<CallOutDetailVO> detailList;

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public Integer getLineId() {
		return lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public Date getCallStartTime() {
		return callStartTime;
	}

	public void setCallStartTime(Date callStartTime) {
		this.callStartTime = callStartTime;
	}

	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
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
		this.agentChannelUuid = agentChannelUuid;
	}

	public String getAgentGroupId() {
		return agentGroupId;
	}

	public void setAgentGroupId(String agentGroupId) {
		this.agentGroupId = agentGroupId;
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

	public Date getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
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

	public String getAccurateIntent​() {
		return accurateIntent​;
	}

	public void setAccurateIntent​(String accurateIntent​) {
		this.accurateIntent​ = accurateIntent​;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getHangupCode() {
		return hangupCode;
	}

	public void setHangupCode(String hangupCode) {
		this.hangupCode = hangupCode;
	}

	public String getOriginateCmd() {
		return originateCmd;
	}

	public void setOriginateCmd(String originateCmd) {
		this.originateCmd = originateCmd;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getFreason() {
		return freason;
	}

	public void setFreason(Integer freason) {
		this.freason = freason;
	}

	public List<CallOutDetailVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<CallOutDetailVO> detailList) {
		this.detailList = detailList;
	}
    
    

}
