package com.guiji.calloutserver.entity;

import java.io.Serializable;
import java.util.List;

public class DispatchPlan implements Serializable {

    private String planUuid;

    private Integer userId;

    private Integer batchId;

    private String phone;

    private String tempId;

    private List<Integer> lineList;

    private boolean isTts;

    private boolean simCall;

    private String orgCode;

    private String agentGroupId;

    private String remarks;

    private String params;

    private static final long serialVersionUID = 1L;

    public String getPlanUuid() {
        return planUuid;
    }

    public void setPlanUuid(String planUuid) {
        this.planUuid = planUuid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public List<Integer> getLineList() {
        return lineList;
    }

    public void setLineList(List<Integer> lineList) {
        this.lineList = lineList;
    }

    public boolean isTts() {
        return isTts;
    }

    public void setTts(boolean tts) {
        isTts = tts;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getAgentGroupId() {
        return agentGroupId;
    }

    public void setAgentGroupId(String agentGroupId) {
        this.agentGroupId = agentGroupId;
    }

    public boolean getSimCall() {
        return simCall;
    }

    public void setSimCall(boolean simCall) {
        this.simCall = simCall;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "DispatchPlan{" +
                "planUuid='" + planUuid + '\'' +
                ", userId=" + userId +
                ", batchId=" + batchId +
                ", phone='" + phone + '\'' +
                ", tempId='" + tempId + '\'' +
                ", lineList=" + lineList +
                ", isTts=" + isTts +
                ", simCall='" + simCall + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", remarks='" + remarks + '\'' +
                ", params='" + params + '\'' +
                ", agentGroupId='" + agentGroupId + '\'' +
                '}';
    }
}
