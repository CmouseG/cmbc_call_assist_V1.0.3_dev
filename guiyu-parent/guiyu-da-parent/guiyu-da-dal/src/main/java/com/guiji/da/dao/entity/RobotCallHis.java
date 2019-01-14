package com.guiji.da.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class RobotCallHis implements Serializable {
    private Integer id;

    private String seqId;

    private String userId;

    private String orgCode;

    private String aiNo;

    private String phoneNo;

    private Date assignTime;

    private String callDate;

    private String templateId;

    private Integer callStatus;

    private Boolean isTts;

    private Integer dialogcount;

    private String industry;

    private Integer modelId;

    private String intentLevel;

    private String reason;

    private String callWav;

    private String crtDate;

    private Date crtTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getAiNo() {
        return aiNo;
    }

    public void setAiNo(String aiNo) {
        this.aiNo = aiNo == null ? null : aiNo.trim();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate == null ? null : callDate.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public Integer getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(Integer callStatus) {
        this.callStatus = callStatus;
    }

    public Boolean getIsTts() {
        return isTts;
    }

    public void setIsTts(Boolean isTts) {
        this.isTts = isTts;
    }

    public Integer getDialogcount() {
        return dialogcount;
    }

    public void setDialogcount(Integer dialogcount) {
        this.dialogcount = dialogcount;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getIntentLevel() {
        return intentLevel;
    }

    public void setIntentLevel(String intentLevel) {
        this.intentLevel = intentLevel == null ? null : intentLevel.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getCallWav() {
        return callWav;
    }

    public void setCallWav(String callWav) {
        this.callWav = callWav == null ? null : callWav.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", seqId=").append(seqId);
        sb.append(", userId=").append(userId);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", aiNo=").append(aiNo);
        sb.append(", phoneNo=").append(phoneNo);
        sb.append(", assignTime=").append(assignTime);
        sb.append(", callDate=").append(callDate);
        sb.append(", templateId=").append(templateId);
        sb.append(", callStatus=").append(callStatus);
        sb.append(", isTts=").append(isTts);
        sb.append(", dialogcount=").append(dialogcount);
        sb.append(", industry=").append(industry);
        sb.append(", modelId=").append(modelId);
        sb.append(", intentLevel=").append(intentLevel);
        sb.append(", reason=").append(reason);
        sb.append(", callWav=").append(callWav);
        sb.append(", crtDate=").append(crtDate);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}