package com.guiji.entity;

public enum CallState {
    /**
     * 刚加入的计划，等待调度
     */
    init,

    /**
     * 被排入呼叫计划，等待FreeSWITCH返回通道状态
     */
    scheduled,

    /**
     * 呼叫被接听
     */
    progress,

    /**
     * 呼叫被接听
     */
    answer,

    /**
     * 转人工
     */
    to_agent,

    /**
     * 座席应答
     */
    agent_answer,

    /**
     * 呼叫成功挂断
     */
    hangup_ok,

    /**
     * 呼叫失败挂断
     */
    hangup_fail
}
