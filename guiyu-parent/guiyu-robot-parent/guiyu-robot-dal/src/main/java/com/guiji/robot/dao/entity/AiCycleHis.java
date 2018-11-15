package com.guiji.robot.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class AiCycleHis implements Serializable {
    private String id;

    private Long userId;

    private String aiNo;

    private String templateId;

    private String aiCyc;

    private String recordDate;

    private String recordTime;

    private Date crtTime;

    private String callNum;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAiNo() {
        return aiNo;
    }

    public void setAiNo(String aiNo) {
        this.aiNo = aiNo == null ? null : aiNo.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getAiCyc() {
        return aiCyc;
    }

    public void setAiCyc(String aiCyc) {
        this.aiCyc = aiCyc == null ? null : aiCyc.trim();
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate == null ? null : recordDate.trim();
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime == null ? null : recordTime.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getCallNum() {
        return callNum;
    }

    public void setCallNum(String callNum) {
        this.callNum = callNum == null ? null : callNum.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", aiNo=").append(aiNo);
        sb.append(", templateId=").append(templateId);
        sb.append(", aiCyc=").append(aiCyc);
        sb.append(", recordDate=").append(recordDate);
        sb.append(", recordTime=").append(recordTime);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", callNum=").append(callNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}