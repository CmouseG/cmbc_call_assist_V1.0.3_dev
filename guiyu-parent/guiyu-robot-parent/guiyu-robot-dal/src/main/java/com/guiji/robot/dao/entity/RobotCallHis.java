package com.guiji.robot.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class RobotCallHis implements Serializable {
    private String id;

    private String seqId;

    private String userId;

    private String aiNo;

    private Date assignTime;

    private String templateId;

    private String callStatus;

    private String crtDate;

    private Date crtTime;

    private String orgCode;

    private String sellbotCallbackJson;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId == null ? null : seqId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAiNo() {
        return aiNo;
    }

    public void setAiNo(String aiNo) {
        this.aiNo = aiNo == null ? null : aiNo.trim();
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus == null ? null : callStatus.trim();
    }

    public String getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(String crtDate) {
        this.crtDate = crtDate == null ? null : crtDate.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getSellbotCallbackJson() {
        return sellbotCallbackJson;
    }

    public void setSellbotCallbackJson(String sellbotCallbackJson) {
        this.sellbotCallbackJson = sellbotCallbackJson == null ? null : sellbotCallbackJson.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", seqId=").append(seqId);
        sb.append(", userId=").append(userId);
        sb.append(", aiNo=").append(aiNo);
        sb.append(", assignTime=").append(assignTime);
        sb.append(", templateId=").append(templateId);
        sb.append(", callStatus=").append(callStatus);
        sb.append(", crtDate=").append(crtDate);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", sellbotCallbackJson=").append(sellbotCallbackJson);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}