package com.guiji.wechat.util.enums;

public enum WeChatErrorEnum {

    ERROR_TRANSFER("1000611", "微信接口调用失败");

    private String key;

    private String desc;

    WeChatErrorEnum(String key, String desc) {
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
