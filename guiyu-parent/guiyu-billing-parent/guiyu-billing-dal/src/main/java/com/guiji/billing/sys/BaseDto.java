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
}
