package com.guiji.botsentence.util.enums;

public enum CategoryEnum {
    COMMON_DIALOGUE(3, "00", "通用对话"),
    MAIN_PROCESS(1, "01", "主流程"),
    BUSINESS_QA(3, "02", "业务问答"),
    ;
    private int key;

    private String typeKey;

    private String desc;

    CategoryEnum(int key, String typeKey, String desc) {
        this.key = key;
        this.typeKey = typeKey;
        this.desc = desc;
    }

    CategoryEnum(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
