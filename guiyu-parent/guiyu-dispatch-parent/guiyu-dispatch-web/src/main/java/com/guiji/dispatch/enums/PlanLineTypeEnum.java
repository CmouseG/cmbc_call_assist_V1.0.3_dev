package com.guiji.dispatch.enums;

/**
 * 计划任务线路类型
 */
public enum PlanLineTypeEnum {

    SIP(1, "SIP"),

    GATEWAY(2, "网关"),
    ;

    private Integer type;

    private String message;

    private PlanLineTypeEnum(Integer type, String message) {
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
