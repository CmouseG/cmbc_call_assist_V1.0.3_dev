package com.guiji.calloutserver.entity;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/1 15:40
 * @Project：guiyu-parent
 * @Description:
 */
public class CCException {
    /**
     * tts下载失败
     */
    public static String TTS_DOWNLOAD_FAIL = "0305001";

    /**
     * 模板缺失
     */
    public static String TEMP_MISS = "0305002";

    /**
     * 线路不存在
     */
    public static String LINE_NOT_EXIST = "0305003";

    /**
     * 线路重复
     */
    public static String LINE_DUPLICATE = "0305004";

    /**
     * 线路忙碌中
     */
    public static String LINE_BUSY = "0305005";

    /**
     * 线路没有并发数(小于等于0)
     */
    public static String LINE_NO_CONCURRENTS = "0305006";

    /**
     * 从调度中心获取的计划为空
     */
    public static String DISPATCH_NO_CALLPLAN = "0305007";

    /**
     * 请求不到sellbot资源
     */
    public static String SELLBOT_NO_RESOURCE = "0305007";

    /**
     * sellbot无响应
     */
    public static String SELLBOT_NO_RESPONSE = "0305008";

    /**
     * 模板不存在
     */
    public static String TEMP_NOTEXISIT = "0305009";
    /**
     * 模板不存在
     */
    public static String GET_WAV_LEN_ERROR = "0305010";


}
