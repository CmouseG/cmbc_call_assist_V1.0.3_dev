package com.guiji.billing.enums;

public enum AcctOrgTypeEnum {

    Agent(1, "代理商"),

    Company(2, "企业"),
    ;

    private Integer type;

    private String message;

    private AcctOrgTypeEnum(Integer type, String message) {
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
