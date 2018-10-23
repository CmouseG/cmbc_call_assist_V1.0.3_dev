package com.guiji.dispatch.model;

public class Schedule {

    private String id;

    //任务id
    private String planUuid;

    //用户id
    private String userId;

    //批次id
    private String batchId;

    //手机号
    private String phone;

    //附加参数，第三方系统唯一标识
    private String attach;

    //变量，多个参数用|分割
    private String params;

    //计划状态，0：未计划 1：计划中 2：计划完成 3：暂停计划 4：停止计划
    private String statusPlan;

    //同步状态，0：未同步 1：已同步
    private String statusSync;

    //重播，0：不重播 非0为重播次数
    private String recall;

    //重播条件
    private String recallParams;

    //呼叫机器人
    private String robot;

    //转人工坐席号
    private String callAgent;

    //当日清除未完成计划
    private String clean;

    //呼叫日期
    private String callDate;

    //呼叫时间
    private String callHour;

    private String gmtCreate;

    private String gmtUpdate;
}
