package com.guiji.web.response;

import lombok.Data;

@Data
public class QueryCallrecord {
    private Long userId;
    private String phoneNum;
    private String label;
    private String reason;
    private String callinTime;
    private String agentAnswerTime;
    private String hangupTime;
    private String accessNum;
    private String crmLoginId;

    private String recordId;

}
