package com.guiji.dispatch.enums;

/**
 * 批量操作类型
 */
public enum PlanOperBatchEnum {

    DEL(1, "批量删除"),

    SUSPEND(2, "批量暂停"),

    STOP(3, "批量停止"),

    RECOVERY(4, "批量恢复"),

    JOIN(5, "批量加入"),
    ;

    private Integer type;

    private String message;

    private PlanOperBatchEnum(Integer type, String message) {
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
