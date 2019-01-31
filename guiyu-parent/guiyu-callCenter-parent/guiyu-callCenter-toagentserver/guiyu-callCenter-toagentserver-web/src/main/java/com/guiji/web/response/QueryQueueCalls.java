package com.guiji.web.response;

import lombok.Data;

@Data
public class QueryQueueCalls {
    private String queueId;
    private Integer waitCount;
    private Integer answeredCount;
    private Integer agentAnsweredCount;
}
