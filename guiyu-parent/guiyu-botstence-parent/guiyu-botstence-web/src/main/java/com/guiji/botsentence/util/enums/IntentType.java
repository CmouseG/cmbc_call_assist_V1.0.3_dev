package com.guiji.botsentence.util.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public enum IntentType {

    COMMON("00", "通用"),
    INDUSTRY("01", "行业")
    ;

    public static final String COMMON_INDUSTRY_ID = "currency";
    public static final String COMMON_INDUSTRY_NAME = "通用库";

    private static Map<String, IntentType> keyToEnumMap = Maps.newHashMap();
    static {
        for (IntentType type: IntentType.values()) {
            keyToEnumMap.put(type.getKey(), type);
        }
    }

    private String key;

    private String desc;


    IntentType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static IntentType getTypeByKey(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return keyToEnumMap.get(key);
    }
}
