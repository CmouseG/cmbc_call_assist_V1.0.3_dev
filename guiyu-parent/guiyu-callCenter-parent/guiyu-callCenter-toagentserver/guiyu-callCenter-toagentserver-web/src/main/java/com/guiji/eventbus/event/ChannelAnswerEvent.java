package com.guiji.eventbus.event;

import com.google.common.base.Strings;
import com.guiji.entity.ECallDirection;
import lombok.Data;

@Data
public class ChannelAnswerEvent {
    private String uuid;
    private String callerNum;
    private String calledNum;

    //队列id
    private Long queueId;

    //通话唯一标识
    private String seqId;

    private ECallDirection callDirection;

    /**
     * 是否为转人工的接听事件
     * @return
     */
    public boolean isCallToAgent(){
        return queueId!=null && queueId>0 && !Strings.isNullOrEmpty(seqId);
    }
}
