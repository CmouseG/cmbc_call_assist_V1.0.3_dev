package com.guiji.botsentence.util.enums;

public enum TtsTaskParamEnum {
    IS_PARAM("01", "是TTS变量"),
    NOT_PARAM("02", " 非TTS变量"),
    ;

    private String key;

    private String desc;

    TtsTaskParamEnum(String key, String desc) {
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
