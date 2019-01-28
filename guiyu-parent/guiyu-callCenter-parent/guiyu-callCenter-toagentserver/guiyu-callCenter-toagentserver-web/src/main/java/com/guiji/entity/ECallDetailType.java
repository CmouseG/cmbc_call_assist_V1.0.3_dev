package com.guiji.entity;

public enum ECallDetailType {
    /**
     * 开场白
     */
    INIT,

    /**
     * ASR识别到内容，但是未匹配到sellbot
     */
    UNMATCH,

    /**
     * ASR识别为空
     */
    ASR_EMPTY,

    /**
     * ASR识别为空，
     * 系统在机器人长时间未说话之后，产生的空白事件，让sellbot产生新的播放录音
     * 长时间未说话原因：客户说话未匹配，或者，客户未说话
     */
    ASR_EMPTY_GENERATED,

    /**
     * 转人工(初始状态)
     */
    TOAGENT_INIT,

    /**
     * 转人工（客户说话）
     */
    TOAGENT_CUSTOMER_SAY,

    /**
     * 转人工（座席应答）
     */
    TOAGENT_AGENT_ANSWER,

    /**
     * 正常的对答, 一问对一答
     */
    NORMAL,

    /**
     * sellbot提示通话结束
     */
    END
}
