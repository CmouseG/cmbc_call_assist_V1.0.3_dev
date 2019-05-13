package com.guiji.botsentence.util.enums;

public enum BranchNameEnum {
    enter_branch(""),
    failed_enter_branch(""),
    special("一般分支"),
    negative("拒绝"),
    positive("未拒绝"),
    ;

    private String desc;

    BranchNameEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
