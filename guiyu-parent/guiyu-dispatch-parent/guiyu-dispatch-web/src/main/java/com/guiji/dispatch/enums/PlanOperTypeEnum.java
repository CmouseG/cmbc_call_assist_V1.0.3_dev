package com.guiji.dispatch.enums;

/**
 * 计划任务线路类型
 */
public enum PlanOperTypeEnum {

    ALL(1, "全选"),

    CHECK(2, "只勾选"),

    NO_CHECK(3, "全选去勾"),
    ;

    private Integer type;

    private String message;

    private PlanOperTypeEnum(Integer type, String message) {
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
