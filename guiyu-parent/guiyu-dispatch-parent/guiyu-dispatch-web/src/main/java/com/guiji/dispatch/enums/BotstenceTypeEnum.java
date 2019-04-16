package com.guiji.dispatch.enums;

/**
 * 计划任务线路类型
 */
public enum BotstenceTypeEnum {

    DEFAULT(1, "默认"),

    FL(2, "飞龙"),
    ;

    private Integer type;

    private String message;

    private BotstenceTypeEnum(Integer type, String message) {
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
