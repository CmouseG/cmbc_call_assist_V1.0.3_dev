package com.guiji.billing.enums;

public enum AcctChargingDeductedEnum {

    CHARGING(0, "扣费"),

    NO_CHARGING(1, "不扣费"),
    ;

    private Integer status;

    private String message;

    private AcctChargingDeductedEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
