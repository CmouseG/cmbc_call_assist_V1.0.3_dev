package com.guiji.billing.enums;

public enum AcctChargingFeeModeEnum {

    BANK_RECHARGE(1, "银行充值"),

    ONLINE_RECHARGE(2, "在线充值"),

    CALL_RESUME(3, "通话消费"),
    ;

    private Integer feeCode;

    private String message;

    private AcctChargingFeeModeEnum(Integer feeCode, String message) {
        this.feeCode = feeCode;
        this.message = message;
    }

    public Integer getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(Integer feeCode) {
        this.feeCode = feeCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
