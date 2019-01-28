package com.guiji.web.request;

import com.guiji.entity.EAnswerType;
import com.guiji.entity.EUserRole;
import com.guiji.entity.EUserState;
import lombok.Data;

@Data
public class CrmUserVO {
    private Long userId;
    private String crmLoginId;
    private String agentName;
    private String agentPwd;
    private String mobile;
    private EUserState agentState;
    private EAnswerType answerType;
    private EUserRole userRole;
    private String orgCode;

}
