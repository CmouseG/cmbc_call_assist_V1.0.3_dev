package com.guiji.billing.enums;

public enum ChargingUnitPriceTypeEnum {

    SECOND(1, "秒"),

    MINUTE(2, "分钟"),

    HOUR(3, "小时"),

    DAY(4, "天"),

    MONTH(5, "月"),

    YEAR(6, "年"),
    ;

    private Integer type;

    private String message;

    private ChargingUnitPriceTypeEnum(Integer type, String message) {
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
