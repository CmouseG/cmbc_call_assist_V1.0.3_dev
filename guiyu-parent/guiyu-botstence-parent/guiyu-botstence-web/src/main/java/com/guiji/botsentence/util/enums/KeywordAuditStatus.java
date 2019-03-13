package com.guiji.botsentence.util.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum KeywordAuditStatus {
    //    审核状态:0-待审核;1-已加入模板;2-已删除
    WAIT_AUDIT(0, "待审核"),
    JOINED(1, "已加入模板"),
    DELETED(2, "已删除"),
    ;

    private static Map<Integer, KeywordAuditStatus> keyToEnumMap = Maps.newHashMap();

    static {
        for (KeywordAuditStatus status: KeywordAuditStatus.values()) {
            keyToEnumMap.put(status.getKey(), status);
        }
    }


    KeywordAuditStatus(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public static KeywordAuditStatus getStatusByKey(Integer key){
        return keyToEnumMap.get(key);
    }

    private int key;

    private String desc;

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
