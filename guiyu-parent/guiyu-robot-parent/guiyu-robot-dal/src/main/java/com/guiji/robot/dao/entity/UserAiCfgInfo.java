package com.guiji.robot.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class UserAiCfgInfo implements Serializable {
    private String id;

    private String userId;

    private Integer aiNum;

    private String assignLevel;

    private String templateIds;

    private String openDate;

    private String invalidDate;

    private String status;

    private String invalidPolicy;

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

    public Integer getAiNum() {
        return aiNum;
    }

    public void setAiNum(Integer aiNum) {
        this.aiNum = aiNum;
    }

    public String getAssignLevel() {
        return assignLevel;
    }

    public void setAssignLevel(String assignLevel) {
        this.assignLevel = assignLevel == null ? null : assignLevel.trim();
    }

    public String getTemplateIds() {
        return templateIds;
    }

    public void setTemplateIds(String templateIds) {
        this.templateIds = templateIds == null ? null : templateIds.trim();
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate == null ? null : openDate.trim();
    }

    public String getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(String invalidDate) {
        this.invalidDate = invalidDate == null ? null : invalidDate.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getInvalidPolicy() {
        return invalidPolicy;
    }

    public void setInvalidPolicy(String invalidPolicy) {
        this.invalidPolicy = invalidPolicy == null ? null : invalidPolicy.trim();
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
        sb.append(", aiNum=").append(aiNum);
        sb.append(", assignLevel=").append(assignLevel);
        sb.append(", templateIds=").append(templateIds);
        sb.append(", openDate=").append(openDate);
        sb.append(", invalidDate=").append(invalidDate);
        sb.append(", status=").append(status);
        sb.append(", invalidPolicy=").append(invalidPolicy);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}