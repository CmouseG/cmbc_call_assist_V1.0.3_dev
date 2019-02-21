package com.guiji.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 *用户账户
 */
public class BillingUserAcctBean extends BaseBean {

    private static final long serialVersionUID = 1278931979000335551L;

    private String accountId;

    private String companyId;

    private String companyName;

    /**
     * 企业编码(企业唯一)
     */
    private String orgCode;

    /**
     * 企业组织类型  1-代理商 2-企业
     */
    private Integer orgType;

    /**
     * 账户金额
     */
    private BigDecimal amount;

    /**
     * 可用金额
     */
    private BigDecimal availableBalance;

    /**
     * 冻结金额
     */
    private BigDecimal freezingAmount;

    /**
     * 有效开始时间
     */
    private Date beginTime;

    /**
     * 有效结束时间
     */
    private Date endTime;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getFreezingAmount() {
        return freezingAmount;
    }

    public void setFreezingAmount(BigDecimal freezingAmount) {
        this.freezingAmount = freezingAmount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }
}
