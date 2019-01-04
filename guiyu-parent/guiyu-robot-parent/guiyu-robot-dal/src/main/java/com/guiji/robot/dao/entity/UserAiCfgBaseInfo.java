package com.guiji.robot.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class UserAiCfgBaseInfo implements Serializable {
    private Integer id;

    private String userId;

    private String orgCode;

    private Integer aiTotalNum;

    private String templateIds;

    private Date crtTime;

    private String crtUser;

    private Date updateTime;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getAiTotalNum() {
        return aiTotalNum;
    }

    public void setAiTotalNum(Integer aiTotalNum) {
        this.aiTotalNum = aiTotalNum;
    }

    public String getTemplateIds() {
        return templateIds;
    }

    public void setTemplateIds(String templateIds) {
        this.templateIds = templateIds == null ? null : templateIds.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser == null ? null : crtUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", aiTotalNum=").append(aiTotalNum);
        sb.append(", templateIds=").append(templateIds);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}