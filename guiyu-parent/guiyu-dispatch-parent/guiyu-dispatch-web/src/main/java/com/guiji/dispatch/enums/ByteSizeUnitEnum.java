package com.guiji.dispatch.enums;

/**
 * 计划任务线路类型
 */
public enum ByteSizeUnitEnum {

    KB(1, "KB"),

    MB(2, "MB"),

    GB(3, "GB"),
    ;

    private Integer unit;

    private String message;

    private ByteSizeUnitEnum(Integer unit, String message) {
        this.unit = unit;
        this.message = message;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}
