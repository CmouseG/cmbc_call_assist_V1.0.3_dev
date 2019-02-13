package com.guiji.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户统计
 */
public class BillingAcctChargingTotal extends BaseBean {

    private static final long serialVersionUID = 3141887925658533882L;

    private String accountId    ;

    /**
     * 类型 1-日 2-月
     */
    private Integer type          ;

    /**
     * 呼叫时长
     */
    private Long callDuration ;

    private String callTime     ;

    /**
     * 消费金额
     */
    private BigDecimal consumeAmount;

    /**
     * 统计日期，例如：yyyy-MM-dd
     */
    private String totalDate;

    /**
     * 统计月份，格式yyyy-MM
     */
    private String totalMonth;

    private Date statTime     ;

    private Integer statStatus   ;

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

    public Long getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Long callDuration) {
        this.callDuration = callDuration;
    }

    public BigDecimal getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(BigDecimal consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public Date getStatTime() {
        return statTime;
    }

    public void setStatTime(Date statTime) {
        this.statTime = statTime;
    }

    public Integer getStatStatus() {
        return statStatus;
    }

    public void setStatStatus(Integer statStatus) {
        this.statStatus = statStatus;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(String totalDate) {
        this.totalDate = totalDate;
    }

    public String getTotalMonth() {
        return totalMonth;
    }

    public void setTotalMonth(String totalMonth) {
        this.totalMonth = totalMonth;
    }
}
