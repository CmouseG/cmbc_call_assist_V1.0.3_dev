package com.guiji.billing.sys;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseDto implements Serializable {

    /**
     * 企业组织编码
     */
    private String orgCode;

    /**
     * 企业用户ID
     */
    private String userId;

    /**
     * 数据查询权限（1-本人;2-本组织;3-本组织及下级组织）
     */
    private  Integer authLevel;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(Integer authLevel) {
        this.authLevel = authLevel;
    }
}
