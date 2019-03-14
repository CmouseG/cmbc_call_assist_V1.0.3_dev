package com.guiji.eventbus.event;

import com.google.common.base.Strings;
import com.guiji.entity.ECallDirection;
import lombok.Data;

@Data
public class ChannelAnswerEvent {
    private String uuid;
    private String callerNum;
    private String calledNum;

    /**
     * 队列id
     */
    private Long queueId;

    /**
     * 座席id，用于协呼
     */
    private String agentId;

    /**
     * 是否为协呼
     */
    private Boolean isXiehu;

    /**
     * 通话唯一标识
     */
    private String seqId;

    /**
     * 呼叫方向，是呼入转人工，还是呼出转人工
     */
    private ECallDirection callDirection;

    /**
     * 另一条腿的uuid
     */
    private String otherLegUuid;

    /**
     * 是否为转人工的接听事件
     * @return
     */
    public boolean isCallToAgent(){
        return queueId!=null && queueId>0 && !Strings.isNullOrEmpty(seqId);
    }
}
