package com.guiji.calloutserver.entity;

import java.io.Serializable;

public class DispatchPlan implements Serializable {

    private String planUuid;

    private Integer userId;

    private Integer batchId;

    private String phone;

    private String tempId;

    private Integer line;

    private boolean isTts;

    private String orgCode;

    private String agentGroupId;

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

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
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

    @Override
    public String toString() {
        return "DispatchPlan{" +
                "planUuid='" + planUuid + '\'' +
                ", userId=" + userId +
                ", batchId=" + batchId +
                ", phone='" + phone + '\'' +
                ", tempId='" + tempId + '\'' +
                ", line=" + line +
                ", isTts=" + isTts +
                ", orgCode='" + orgCode + '\'' +
                ", agentGroupId='" + agentGroupId + '\'' +
                '}';
    }
}
