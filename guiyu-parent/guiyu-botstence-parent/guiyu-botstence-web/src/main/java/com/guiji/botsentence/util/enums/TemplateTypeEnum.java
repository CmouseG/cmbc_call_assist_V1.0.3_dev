package com.guiji.botsentence.util.enums;

public enum  TemplateTypeEnum {
    FOR_PUBLIC("01", "公开"),
    FOR_PERSONAL("02", "个人");

    private String key;
    private String desc;

    TemplateTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
