package com.guiji.botsentence.util.enums;

public enum KeywordsType {

    SINGLE("自定义关键词"),
    COMBINATION("组合关键词"),
    EXACT_MATCH("完全匹配关键词"),
    OTHER("其他关键词");

    private String desc;

    KeywordsType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
