package com.guiji.billing.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TotalChargingItemDetailVo implements Serializable {

    private static final long serialVersionUID = -2483301540617847667L;
    /**
     * 手机号码
     */
    private String phone;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 通话时长
     */
    private String operDurationStr;

    /**
     * 通话时长：分钟
     */
    private Integer operDurationM;

    /**
     * 实际扣费金额
     */
    private BigDecimal amount;

    /**
     * 计费金额
     */
    private BigDecimal chargingAmount;

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
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public BigDecimal getChargingAmount() {
        return chargingAmount;
    }

    public void setChargingAmount(BigDecimal chargingAmount) {
        this.chargingAmount = chargingAmount;
    }

    public Integer getOperDurationM() {
        return operDurationM;
    }

    public void setOperDurationM(Integer operDurationM) {
        this.operDurationM = operDurationM;
    }
}
