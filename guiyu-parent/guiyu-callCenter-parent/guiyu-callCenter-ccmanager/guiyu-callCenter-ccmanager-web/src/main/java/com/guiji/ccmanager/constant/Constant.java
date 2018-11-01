package com.guiji.ccmanager.constant;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/30 0030 16:14
 * @Description:
 */
public class Constant {

    public static final String SUCCESS_FSMANAGER = "0301000";
    public static final String SUCCESS_FSLINE = "0302000";
    public static final String SUCCESS_CCMANAGER = "0303000";
    public static final String SUCCESS_CCWEB = "0304000";
    public static final String SUCCESS_CALLOUTSERVER = "0305000";
    public static final String SUCCESS_CALLINSERVER= "0306000";

    public static final String PROTOCOL = "http://";

    public static final String SUCCESS_COMMON = "0";

    public static final String SERVER_NAME_CALLOUTSERVER = "guiyu-callcenter-calloutserver";
    public static final String SERVER_NAME_FSLINE = "guiyu-callcenter-fsline";

    public static final String ERROR_PARAM = "0303001";
    public static final String ERROR_LINE_NOTEXIST = "0303002";
    public static final String ERROR_LINE_RUNNING = "0303003";
    public static final String ERROR_TEMP_NOTEXIST = "0303004";
    public static final String ERROR_TEMP_DOWNLOADFAIL = "0303005";



    public static final int CALLSTATE_INIT = 0;//刚加入的计划，等待调度
    public static final int CALLSTATE_SCHEDULED = 1;//被排入呼叫计划，等待FreeSWITCH返回通道状态
    public static final int CALLSTATE_PROGRESS = 2;//呼叫振铃
    public static final int CALLSTATE_ANSWER = 3;//呼叫被接听
    public static final int CALLSTATE_TO_AGENT = 4;//转人工
    public static final int CALLSTATE_AGENT_ANSWER = 5;//座席应答管段刚加入的计划，等待调度
    public static final int CALLSTATE_HANGUP = 6;//挂断

}
