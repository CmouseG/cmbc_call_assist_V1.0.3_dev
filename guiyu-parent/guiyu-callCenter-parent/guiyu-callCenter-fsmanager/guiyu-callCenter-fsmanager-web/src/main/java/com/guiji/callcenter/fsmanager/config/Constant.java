package com.guiji.callcenter.fsmanager.config;

public class Constant {

    public static final String CONFIG_TYPE_DIALPLAN = "dialplan";
    public static final String CONFIG_TYPE_GATEWAY = "gateway";

    public static final String PROTOCOL = "http://";

    public static final String SERVER_NAME_FSAGENT = "guiyu-callcenter-fsagent";
    public static final String SERVER_NAME_CALLOUTSERVER = "guiyu-callcenter-calloutserver";
    public static final String SERVER_NAME_CALLINSERVER = "guiyu-callcenter-callinserver";
    public static final String SERVER_NAME_CALLCENTER = "guiyu-callcenter-callcenter";
    public static final String SERVER_NAME_FSLINE = "guiyu-callcenter-fsline";
    public static final String SERVER_NAME_SIMAGENT = "guiyu-callcenter-simagent";

    public static final String FREESWITCH_ROLE_CALLOUT = "callout";
    public static final String FREESWITCH_ROLE_CALLIN = "callin";
    public static final String FREESWITCH_ROLE_TOAGENT = "toagent";
    public static final String FREESWITCH_ROLE_LINE = "line";

    public static final String ERROR_CODE_PARAM= "0301001";
    public static final String ERROR_CODE_NONE_FREESWITCH= "0301002";//申请freeswitch资源失败，空闲且正常的freeswitch为空
    public static final String ERROR_CODE_LINE_REPEAT= "0301003";//创建线路失败，线路名称重复
}
