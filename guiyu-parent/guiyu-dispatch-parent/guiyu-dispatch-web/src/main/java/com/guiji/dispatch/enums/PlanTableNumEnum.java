package com.guiji.dispatch.enums;

/**
 * 计划任务线路类型
 */
public enum PlanTableNumEnum {

    ZERO(0, "表0"),

    ONE(1, "表1"),

    TWO(2, "表2"),
    ;

    private Integer num;

    private String message;

    private PlanTableNumEnum(Integer num, String message) {
        this.num = num;
        this.message = message;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
