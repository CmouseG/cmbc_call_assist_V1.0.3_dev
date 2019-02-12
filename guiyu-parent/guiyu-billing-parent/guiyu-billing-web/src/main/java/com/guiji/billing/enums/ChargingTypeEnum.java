package com.guiji.billing.enums;

public enum ChargingTypeEnum {

    RECHARGE(1, "充值"),

    CONSUME(2, "消费"),

    INCOME(3, "收入"),
    ;

    private Integer type;

    private String message;

    private ChargingTypeEnum(Integer type, String message) {
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
