package com.guiji.botsentence.util.enums;

public enum TtsTaskTypeEnum {
    CALL_RECORD("01", "通话录音"),
    BACKUP_RECORD("02", "备用话术录音"),
    ;


    private String key;

    private String desc;

    TtsTaskTypeEnum(String key, String desc) {
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
