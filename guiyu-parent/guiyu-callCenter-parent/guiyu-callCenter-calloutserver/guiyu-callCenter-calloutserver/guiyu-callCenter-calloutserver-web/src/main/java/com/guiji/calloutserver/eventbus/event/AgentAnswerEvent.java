package com.guiji.calloutserver.eventbus.event;

import lombok.Data;

@Data
public class AgentAnswerEvent {
    private String agentId;
    private String agentGroupId;
    private String agentUuid;
    private String agentAnswerTime;

    private String customerUuid;
    private String customerNum;
}
