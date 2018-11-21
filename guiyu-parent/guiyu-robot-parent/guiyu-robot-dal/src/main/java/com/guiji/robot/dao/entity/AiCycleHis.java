package com.guiji.robot.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class AiCycleHis implements Serializable {
    private String id;

    private String userId;

    private String aiNo;

    private String templateId;

    private String assignDate;

    private String assignTime;

    private String taskbackDate;

    private String taskbackTime;

    private Long callNum;

    private Date crtTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate == null ? null : assignDate.trim();
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime == null ? null : assignTime.trim();
    }

    public String getTaskbackDate() {
        return taskbackDate;
    }

    public void setTaskbackDate(String taskbackDate) {
        this.taskbackDate = taskbackDate == null ? null : taskbackDate.trim();
    }

    public String getTaskbackTime() {
        return taskbackTime;
    }

    public void setTaskbackTime(String taskbackTime) {
        this.taskbackTime = taskbackTime == null ? null : taskbackTime.trim();
    }

    public Long getCallNum() {
        return callNum;
    }

    public void setCallNum(Long callNum) {
        this.callNum = callNum;
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
        sb.append(", aiNo=").append(aiNo);
        sb.append(", templateId=").append(templateId);
        sb.append(", assignDate=").append(assignDate);
        sb.append(", assignTime=").append(assignTime);
        sb.append(", taskbackDate=").append(taskbackDate);
        sb.append(", taskbackTime=").append(taskbackTime);
        sb.append(", callNum=").append(callNum);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}