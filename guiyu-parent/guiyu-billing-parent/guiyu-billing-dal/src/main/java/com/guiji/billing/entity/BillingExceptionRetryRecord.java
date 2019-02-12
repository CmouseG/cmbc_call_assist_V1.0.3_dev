package com.guiji.billing.entity;

import java.util.Date;

/**
 * 异常重试记录
 */
public class BillingExceptionRetryRecord extends BaseBean {

    private static final long serialVersionUID = -3549884634630993908L;

    private String accountId  ;

    private Integer type        ;

    private String keyId      ;

    private Date retryTimes ;

    private Integer retryStatus;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Date getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Date retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Integer getRetryStatus() {
        return retryStatus;
    }

    public void setRetryStatus(Integer retryStatus) {
        this.retryStatus = retryStatus;
    }
}
