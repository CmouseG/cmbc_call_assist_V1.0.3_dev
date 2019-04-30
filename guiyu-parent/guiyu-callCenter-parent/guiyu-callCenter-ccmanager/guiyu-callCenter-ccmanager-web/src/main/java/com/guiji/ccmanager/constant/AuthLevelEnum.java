package com.guiji.ccmanager.constant;

public enum AuthLevelEnum {

    USER(1, "用户"),

    ORG(2, "本组织"),

    ORG_EXT(3, "本组织及以下组织");

    private Integer level;

    private String message;

    private AuthLevelEnum(Integer level, String message) {
        this.level = level;
        this.message = message;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
