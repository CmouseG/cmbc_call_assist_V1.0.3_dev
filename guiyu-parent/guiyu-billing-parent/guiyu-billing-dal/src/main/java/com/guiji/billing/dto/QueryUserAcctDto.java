package com.guiji.billing.dto;

import com.guiji.billing.sys.PageDto;

public class QueryUserAcctDto extends PageDto {

    private Integer id;

    /**
     * 企业账户ID
     */
    private String accountId;

    /**
     * 企业编码
     */
    private String orgCode;

    /**
     * 企业下员工用户ID
     */
    private String userId;

    /**
     * 企业名称
     */
    private String companyName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

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
