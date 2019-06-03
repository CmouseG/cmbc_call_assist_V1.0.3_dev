package com.guiji.botsentence.util.enums;

public enum TtsParamTypeEnum {
    NORMAL("normal", ""),
    MONEY("money", ""),
    NAME("name", ""),
    ;

    TtsParamTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    private String key;

    private String desc;

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
