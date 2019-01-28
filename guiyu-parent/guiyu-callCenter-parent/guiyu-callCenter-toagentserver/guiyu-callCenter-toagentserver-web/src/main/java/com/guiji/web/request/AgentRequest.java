package com.guiji.web.request;

import com.guiji.entity.EAnswerType;
import com.guiji.entity.EUserState;
import lombok.Data;

@Data
public class AgentRequest {
    private Long userId;
    private String crmLoginId;
    private String agentName;
    private String agentPwd;
    private String mobile;
    private EUserState agentState;
    private EAnswerType answerType;
    private Long queueId;
}
