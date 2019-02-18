package com.guiji.billing.vo;

import com.guiji.billing.entity.BillingUserAcctBean;

public class UserAcctThresholdVo extends BillingUserAcctBean {

    /**
     * 阈值KEY
     */
    private String thresholdKey;

    /**
     * 阈值Value
     */
    private String thresholdValue;

    public String getThresholdKey() {
        return thresholdKey;
    }

    public void setThresholdKey(String thresholdKey) {
        this.thresholdKey = thresholdKey;
    }

    public String getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(String thresholdValue) {
        this.thresholdValue = thresholdValue;
    }
}
