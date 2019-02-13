package com.guiji.billing.enums;

public enum ChargingTremTypeEnum {

    TIME_DURATION(1, "时长"),

    ROUTE_NUM(2, "路数"),

    MONTH(3, "月度"),
    ;

    private Integer type;

    private String message;

    private ChargingTremTypeEnum(Integer type, String message) {
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
