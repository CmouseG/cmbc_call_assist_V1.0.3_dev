package com.guiji.eventbus.event;

import com.guiji.entity.ECallDirection;
import lombok.Data;

@Data
public class AgentAnswerEvent {
    private String agentId;
    private String agentGroupId;
    private String agentUuid;
    private String agentAnswerTime;

    private String seqId;

    private String customerUuid;
    private String customerNum;

    private ECallDirection callDirection;
}
