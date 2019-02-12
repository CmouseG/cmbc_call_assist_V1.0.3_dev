package com.guiji.billing.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TotalChargingItemDetailVo implements Serializable {

    private String phone;

    private Date beginTime;

    private Date EndTime;

    private String operDurationStr;

    private BigDecimal amount;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    public String getOperDurationStr() {
        return operDurationStr;
    }

    public void setOperDurationStr(String operDurationStr) {
        this.operDurationStr = operDurationStr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
