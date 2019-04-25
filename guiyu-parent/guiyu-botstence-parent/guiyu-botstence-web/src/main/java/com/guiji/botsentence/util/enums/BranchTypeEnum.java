package com.guiji.botsentence.util.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum BranchTypeEnum {

    NORMAL("01", "一般分支"),
    NOT_REJECT("02", "未拒绝")
    ;

    private static Map<String,BranchTypeEnum> keyToEnumMap = Maps.newHashMap();

    static {
        for(BranchTypeEnum typeEnum : BranchTypeEnum.values()){
            keyToEnumMap.put(typeEnum.key, typeEnum);
        }
    }

    private String key;

    private String desc;

    BranchTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public static BranchTypeEnum getTypeByKey(String key){
        return keyToEnumMap.get(key);
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
