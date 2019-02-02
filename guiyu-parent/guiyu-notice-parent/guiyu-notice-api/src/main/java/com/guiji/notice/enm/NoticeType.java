package com.guiji.notice.enm;

public enum NoticeType {

    intentional_customer("意向客户",1,0),
    task_finish("任务完成",2,0),
    unconnected_alert("连续未接通警报",3,0),
    line_error("线路报错",4,0),

    announcement("公告",5,1),

    money_not_enough("余额不足",6,2),
    recharge_to_account("充值到账",7,2),
    account_maturity("账户到期",8,2);

    private NoticeType(String desc,int value,int parent){
        this.desc=desc;
        this.value=value;
        this.parent=parent;
    }
    private String desc;
    private int value;
    private int parent;
    public String getDesc() {
        return desc;
    }
    public int getValue() {
        return value;
    }
    public int getParent() {
        return parent;
    }
}
