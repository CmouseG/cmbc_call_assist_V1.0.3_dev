package com.guiji.billing.enums;

public enum AcctArrearageStatusEnum {

    NORMAL(0, "正常"),

    ARREARAGE(-1, "欠费"),
    ;

    private Integer status;

    private String message;

    private AcctArrearageStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
