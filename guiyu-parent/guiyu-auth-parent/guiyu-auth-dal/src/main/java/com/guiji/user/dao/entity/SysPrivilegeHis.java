package com.guiji.user.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class SysPrivilegeHis implements Serializable {
    private Integer id;

    private Integer privilegeId;

    private String authId;

    private Integer authType;

    private String resourceId;

    private String orgCode;

    private Integer resourceType;

    private Integer crtUser;

    private Date crtTime;

    private Date updateTime;

    private Integer updateUser;

    private Integer doUser;

    private Date doTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(Integer crtUser) {
        this.crtUser = crtUser;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getDoUser() {
        return doUser;
    }

    public void setDoUser(Integer doUser) {
        this.doUser = doUser;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", privilegeId=").append(privilegeId);
        sb.append(", authId=").append(authId);
        sb.append(", authType=").append(authType);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", resourceType=").append(resourceType);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", doUser=").append(doUser);
        sb.append(", doTime=").append(doTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}