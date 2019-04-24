package com.guiji.botsentence.util.enums;

public enum CategoryEnum {

    MAIN_PROCESS(1, "主流程"),
    BUSINESS_QA(3, "业务问答"),
    COMMON_DIALOGUE(3, "通用对话"),
    ;
    private int key;

    private String desc;

    CategoryEnum(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
