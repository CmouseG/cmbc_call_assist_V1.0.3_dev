package com.guiji.notice.enm;

public enum NoticeParentType {

    task("任务",0),
    system("系统",1),
    finance("财务",2);

    private String desc;
    private int value;
    NoticeParentType(String desc, int value){
        this.desc = desc;
        this.value = value;
    }
    public String getDesc() {
        return desc;
    }
    public int getValue() {
        return value;
    }



}
