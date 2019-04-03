package com.guiji.web.response;

import lombok.Data;

@Data
public class QueryQueue {
    private Long queueId;
    private String queueName;
    private String userName;
    private String updateTime;
    private Integer lineId;
    private String lineName;
    private Integer agentCount;
    private String orgName;

}
