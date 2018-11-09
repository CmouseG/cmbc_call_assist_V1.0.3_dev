package com.guiji.fsagent.config;

public class Constant {

    public static final String CONFIG_TYPE_DIALPLAN = "dialplan";
    public static final String CONFIG_TYPE_GATEWAY = "gateway";

    public static final String PROTOCOL = "http://";

    public static final String SERVER_NAME_FSMANAGER = "guiyu-callcenter-fsmanager";

    public static final String ERROR_CODE_PARAM= "0300001";
    public static final String ERROR_CODE_NOT_LINE= "0300002";//申请freeswitch资源失败，空闲且正常的freeswitch为空
    public static final String ERROR_CODE_BASE64_ERROR= "0300003";//创建线路失败，线路名称重复
}
