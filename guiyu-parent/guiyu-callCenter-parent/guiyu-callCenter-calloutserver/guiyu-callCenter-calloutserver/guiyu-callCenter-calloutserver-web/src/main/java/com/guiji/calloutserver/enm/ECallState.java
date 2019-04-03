package com.guiji.calloutserver.enm;

public enum ECallState {
    /**
     * 刚加入的计划，等待调度
     */
    init,

    /**
     * 准备呼叫依赖的资源
     */
    call_prepare,

    /**
     * 发起外呼中...
     */
    make_call,

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
    hangup_fail,

    /**
     * 机器人没有资源，无法呼叫结束
     */
    norobot_fail
}
