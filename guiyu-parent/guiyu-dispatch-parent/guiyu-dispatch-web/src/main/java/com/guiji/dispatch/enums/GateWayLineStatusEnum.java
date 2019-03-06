package com.guiji.dispatch.enums;

/**
 * 计划任务线路类型
 */
public enum GateWayLineStatusEnum {

    LEISURE(0, "闲置"),

    OCCUPY(1, "占用"),
    ;

    private Integer state;

    private String message;

    private GateWayLineStatusEnum(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
