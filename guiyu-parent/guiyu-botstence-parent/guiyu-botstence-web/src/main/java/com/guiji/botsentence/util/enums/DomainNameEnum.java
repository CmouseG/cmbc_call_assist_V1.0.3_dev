package com.guiji.botsentence.util.enums;

import com.google.common.collect.Maps;

import java.util.Map;

import static com.guiji.botsentence.util.enums.CategoryEnum.*;

public enum DomainNameEnum {
    NORMAL_QUESTION("一般问题", BUSINESS_QA),
    NO_MATCH_RESPONSE("未匹配响应", COMMON_DIALOGUE),
    END_BUSY("结束_在忙", COMMON_DIALOGUE),
    BUSY("在忙", COMMON_DIALOGUE),
    INVITATION("邀约", MAIN_PROCESS),
    ;

    private static Map<String, DomainNameEnum> nameToEnumMap = Maps.newHashMap();

    static {
        for (DomainNameEnum domain: DomainNameEnum.values()) {
             nameToEnumMap.put(domain.getKey(), domain);
        }
    }

    private String key;

    private CategoryEnum category;

    DomainNameEnum(String key, CategoryEnum category) {
        this.key = key;
        this.category = category;
    }

    public static DomainNameEnum getDomainByName(String domainName){
        return nameToEnumMap.get(domainName);
    }

    public String getKey() {
        return key;
    }

    public CategoryEnum getCategory() {
        return category;
    }
}
