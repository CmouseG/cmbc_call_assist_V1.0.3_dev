package com.guiji.dispatch.enums;

/**
 * 计划任务线路类型
 */
public enum BusiTypeEnum {

    DISPATCH("02", "调度中心"),

    CALLCENTER("03", "呼叫中心"),
    ;

    private String type;

    private String message;

    private BusiTypeEnum(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}
