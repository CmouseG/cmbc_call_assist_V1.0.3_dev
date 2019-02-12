package com.guiji.billing.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BillingTotalChargingConsumerVo implements Serializable {

    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;

    /**
     * 消费金额
     */
    private BigDecimal consumeAmount;

    /**
     * 统计日期
      */
    private String totalTime;

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(BigDecimal consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
