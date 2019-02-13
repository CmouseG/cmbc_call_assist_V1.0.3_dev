package com.guiji.billing.enums;

public enum AcctChargingStatusEnum {

    OFF(0, "停用"),

    START_UP(1, "启用"),
    ;

    private Integer status;

    private String message;

    private AcctChargingStatusEnum(Integer status, String message) {
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
