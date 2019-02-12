package com.guiji.billing.enums;

public enum ChargingTremStatusEnum {

    TIME_DURATION(1, "时长"),

    ROUTE_NUM(2, "路数"),

    MONTH(2, "月度"),
    ;

    private Integer status;

    private String message;

    private ChargingTremStatusEnum(Integer status, String message) {
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
