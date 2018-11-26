package com.guiji.robot.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class UserAiCfgBaseInfo implements Serializable {
    private String id;

    private String userId;

    private Integer aiTotalNum;

    private Date crtTime;

    private String templateIds;

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

    public Integer getAiTotalNum() {
        return aiTotalNum;
    }

    public void setAiTotalNum(Integer aiTotalNum) {
        this.aiTotalNum = aiTotalNum;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getTemplateIds() {
        return templateIds;
    }

    public void setTemplateIds(String templateIds) {
        this.templateIds = templateIds == null ? null : templateIds.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", aiTotalNum=").append(aiTotalNum);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", templateIds=").append(templateIds);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}