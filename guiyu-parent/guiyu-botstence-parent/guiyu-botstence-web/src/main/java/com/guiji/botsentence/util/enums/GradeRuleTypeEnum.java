package com.guiji.botsentence.util.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public enum  GradeRuleTypeEnum {

    TRIGGER_MAIN_PROCESS("01", "触发某个主流程节点"),
    REJECTED_TIMES("02", "拒绝次数"),
    EFFECTIVE_MAIN_PROCESS_DIALOGUE_TIMES("03", "有效主流程对话轮数"),
    CALL_DURATION("04", "通话时长(秒)"),
//    TRIGGER_BUSINESS_QA_TIMES("05", "触发业务问答次数", "一般回答计数"),
    TRIGGER_BUSINESS_QA_TIMES("05", "触发业务问答次数", "业务回答计数"),
    TRIGGER_ONE_BUSINESS_QA("06", "触发某个业务问答"),
    USER_NO_SPEAK_TIMES("07", "用户没说话次数", "空回答计数"),
    EFFECTIVE_DIALOGUE_TIMES("08", "有效对话轮数", "有效回答计数"),
    ;

    private static Map<String, GradeRuleTypeEnum> keyToEnumMap = Maps.newHashMap();

    static {
        for (GradeRuleTypeEnum ruleType : GradeRuleTypeEnum.values()){
            keyToEnumMap.put(ruleType.getKey(), ruleType);
        }
    }

    private String key;

    private String desc;

    private String evaluate;

    GradeRuleTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    GradeRuleTypeEnum(String key, String desc, String evaluate) {
        this.key = key;
        this.desc = desc;
        this.evaluate = evaluate;
    }

    public static GradeRuleTypeEnum getTypeByKey(String key){
        return keyToEnumMap.get(key);
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public String getEvaluate() {
        return evaluate;
    }
}
