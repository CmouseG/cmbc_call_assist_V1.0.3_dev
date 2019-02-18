package com.guiji.billing.constants;

public enum ThresholdKeyEnum {
    //账户
    BalanceEarlyWarning("balanceEarlyWarning","企业余额阈值"),
    ;

    private String key;

    private String message;

    ThresholdKeyEnum(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
