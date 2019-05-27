package com.guiji.botsentence.util.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum SpeechAuditStatusEnum {

    MAKING("00", "制作中"),
    AUDITING("01", "审核中"),
    PASSED("02", "审核通过"),
    NOT_PASSED("03", "审核不通过"),
    ONLINE("04", "已上线"),
    DEPLOYING("05", "部署中"),
    DEPLOY_FAILED("06", "部署失败"),
    ;

    private static Map<String, SpeechAuditStatusEnum> keyToEnumMap = Maps.newHashMap();

    static {
        for (SpeechAuditStatusEnum status: SpeechAuditStatusEnum.values()) {
            keyToEnumMap.put(status.getKey(), status);
        }
    }

    SpeechAuditStatusEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    private String key;

    private String desc;

    public static SpeechAuditStatusEnum getEnumByKey(String key){
        return keyToEnumMap.get(key);
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
