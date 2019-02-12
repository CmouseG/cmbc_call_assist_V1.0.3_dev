package com.guiji.wechat.util.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum EventTypeEnum {

    SUBSCRIBE("subscribe", "关注公众号"),
    SCAN("SCAN", "已关注用户扫描二维码"),
    UNSUBSCRIBE("unsubscribe", "取消关注公众号"),
    CLICK("CLICK", "自定义菜单点击事件"),
    ;
    private String key;

    private String desc;

    private static Map<String, EventTypeEnum> keyToEnumMap = Maps.newHashMap();

    static {
        for (EventTypeEnum e : EventTypeEnum.values()) {
            keyToEnumMap.put(e.getKey(), e);
        }
    }

    EventTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }


    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static EventTypeEnum getEnumByKey(String key) {
        return keyToEnumMap.get(key);
    }
}
