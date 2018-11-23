package com.guiji.calloutserver.enm;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/23 16:07
 * @Project：guiyu-parent
 * @Description:
 */
public enum EFailType {
    /**
     * 无人接听
     */
    NO_ANSWER,

    /**
     * 主叫停机
     */
    CALLER_NO_CHARGE,

    /**
     * 被叫停机
     */
    CALLEE_NO_CHARGE,

    /**
     * 关机或不在服务区
     */
    NO_POWER,

    /**
     * 占线
     */
    LINE_BUSY,

    /**
     * 空号
     */
    NULL_NUMBER,

    /**
     * 呼叫受限
     */
    CALL_NOT_ALLOWED,

    /**
     * 用户挂断
     */
    CALLEE_HANGUP,

    /**
     * 无效号码
     */
    INVALID_NUMBER,

    /**
     * 已接通
     */
    ALREADY_ANSWER;
}
