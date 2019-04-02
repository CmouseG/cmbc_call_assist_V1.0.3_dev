package com.guiji.dispatch.sys;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseDto implements Serializable {

    /**
     * 企业组织编码
     */
    private String operOrgCode;

    /**
     * 企业组织编码
     */
    private Integer operOrgId;

    /**
     * 企业用户ID
     */
    private String operUserId;

    /**
     * 是否是超级管理员
     */
    private boolean isSuperAdmin;

    /**
     * 该用户号码是否脱敏
     */
    private Integer isDesensitization;

    /**
     * 数据查询权限（1-本人;2-本组织;3-本组织及下级组织）
     */
    private Integer authLevel;

    public String getOperOrgCode() {
        return operOrgCode;
    }

    public void setOperOrgCode(String operOrgCode) {
        this.operOrgCode = operOrgCode;
    }

    public String getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public Integer getIsDesensitization() {
        return isDesensitization;
    }

    public void setIsDesensitization(Integer isDesensitization) {
        this.isDesensitization = isDesensitization;
    }

    public Integer getOperOrgId() {
        return operOrgId;
    }

    public void setOperOrgId(Integer operOrgId) {
        this.operOrgId = operOrgId;
    }

    public Integer getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(Integer authLevel) {
        this.authLevel = authLevel;
    }
}
