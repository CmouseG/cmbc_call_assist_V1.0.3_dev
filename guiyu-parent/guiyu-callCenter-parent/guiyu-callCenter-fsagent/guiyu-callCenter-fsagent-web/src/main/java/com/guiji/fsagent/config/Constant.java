package com.guiji.fsagent.config;

public class Constant {

    public static final String CONFIG_TYPE_DIALPLAN = "dialplan";
    public static final String CONFIG_TYPE_GATEWAY = "gateway";

    public static final String PROTOCOL = "http://";

    public static final String SERVER_NAME_FSMANAGER = "guiyu-callcenter-fsmanager";

    public static final String ERROR_CODE_PARAM= "0300001";
    public static final String ERROR_CODE_NOT_LINE= "0300002";//更新配置信息通知请求失败，请求参数type不是line
    public static final String ERROR_CODE_BASE64_ERROR= "0300003";//下载配置文件失败，下载新得xml后转base64过程中异常
}
