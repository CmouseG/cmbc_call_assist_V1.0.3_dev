package com.guiji.robot.model;

import lombok.Data;

/**
 * @Classname HangupRes
 * @Description 挂断后，sellbot/feilong返回的信息
 * @Date 2019/5/6 16:43
 * @Created by qinghua
 */

@Data
public class HangupRes {

    /**
     * 对话轮数
     */
    private Integer conversation_count;

    /**
     * 当前session数
     */
    private Integer sessions;

    /**
     * 异常信息
     */
    private String exception_message;

    /**
     * 意向标签
     */
    private String accurate_intent;

    /**
     * 模板版本
     */
    private String cfg_ver;

    /**
     * 意向
     */
    private String intent;

    /**
     * 挂断原因
     */
    private String reason;

    /**
     * 对话
     */
    private String sentence;

    /**
     * 一通电话seqId
     */
    private String seqid;

    /**
     *
     */
    private String state;

    /**
     * 耗时，毫秒
     */
    private String used_time_ms;

    /**
     *
     */
    private String reserved_action_return;
}
