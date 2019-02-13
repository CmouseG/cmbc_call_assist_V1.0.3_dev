package com.guiji.vo;

import java.math.BigDecimal;

/**
 *用户账户
 */
public class BillingUserAcctVo {

    private static final long serialVersionUID = 1278931979000335551L;

    private String accountId;

    private String companyId;

    private String companyName;

    /**
     * 企业编码(企业唯一)
     */
    private String orgCode;

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
}
