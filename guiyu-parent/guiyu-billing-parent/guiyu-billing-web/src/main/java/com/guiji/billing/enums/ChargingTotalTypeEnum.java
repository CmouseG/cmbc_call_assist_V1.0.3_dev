package com.guiji.billing.enums;

public enum ChargingTotalTypeEnum {

    DAY(1, "日"),

    MONTH(2, "月"),
    ;

    private Integer type;

    private String message;

    private ChargingTotalTypeEnum(Integer type, String message) {
        this.type = type;
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
