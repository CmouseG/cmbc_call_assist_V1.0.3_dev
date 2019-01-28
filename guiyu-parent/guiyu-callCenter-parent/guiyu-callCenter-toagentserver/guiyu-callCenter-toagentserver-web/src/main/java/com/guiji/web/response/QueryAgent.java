package com.guiji.web.response;

import com.guiji.entity.EAnswerType;
import com.guiji.entity.EUserState;
import lombok.Data;

@Data
public class QueryAgent {
    private Long userId;
    private String crmLoginId;
    private String agentName;
    private String agentPwd;
    private String mobile;
    private EUserState agentState;
    private Long queueId;
    private EAnswerType answerType;
    private String createDate;
    private Long creator;
    private String creatorName;
    private String queueName;


}
