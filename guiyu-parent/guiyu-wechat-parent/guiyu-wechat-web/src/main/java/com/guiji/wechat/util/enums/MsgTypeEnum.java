package com.guiji.wechat.util.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum MsgTypeEnum {

    EVENT("event", "事件推送"),
    TEXT("text", "文本"),
    MUSIC("music", "音乐"),
    NEWS("news", "图文"),
    IMAGE("image", "图片"),
    LINK("link", "链接"),
    LOCATION("location", "地理位置"),
    VOICE("voice", "音频"),
    ;

    private String key;

    private String desc;

    private static Map<String, MsgTypeEnum> keyToEnumMap = Maps.newHashMap();

    static {
        for (MsgTypeEnum e : MsgTypeEnum.values()) {
            keyToEnumMap.put(e.getKey(), e);
        }
    }

    MsgTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }


    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static MsgTypeEnum getEnumByKey(String key) {
        return keyToEnumMap.get(key);
    }
}
