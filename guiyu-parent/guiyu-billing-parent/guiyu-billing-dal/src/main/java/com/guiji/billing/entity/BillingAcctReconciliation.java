package com.guiji.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 对账记录
 */
public class BillingAcctReconciliation extends BaseBean {

    private String accountId           ;

    private Date operTime            ;

    private Integer operStatus          ;

    private String operDetails         ;

    private Date startTime           ;

    private Date endTime             ;

    private String myChargingId        ;

    private BigDecimal serviceAmount       ;

    private BigDecimal chargingCenterAmount;

    private Integer status               ;

    private String fixUserId          ;

    private String fixDetails          ;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public Integer getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(Integer operStatus) {
        this.operStatus = operStatus;
    }

    public String getOperDetails() {
        return operDetails;
    }

    public void setOperDetails(String operDetails) {
        this.operDetails = operDetails;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMyChargingId() {
        return myChargingId;
    }

    public void setMyChargingId(String myChargingId) {
        this.myChargingId = myChargingId;
    }

    public BigDecimal getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(BigDecimal serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFixUserId() {
        return fixUserId;
    }

    public void setFixUserId(String fixUserId) {
        this.fixUserId = fixUserId;
    }

    public String getFixDetails() {
        return fixDetails;
    }

    public void setFixDetails(String fixDetails) {
        this.fixDetails = fixDetails;
    }

    public BigDecimal getChargingCenterAmount() {
        return chargingCenterAmount;
    }

    public void setChargingCenterAmount(BigDecimal chargingCenterAmount) {
        this.chargingCenterAmount = chargingCenterAmount;
    }
}
