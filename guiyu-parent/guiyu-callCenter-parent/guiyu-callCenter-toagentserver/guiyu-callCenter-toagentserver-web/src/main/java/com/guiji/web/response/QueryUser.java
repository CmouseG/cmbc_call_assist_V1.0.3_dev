package com.guiji.web.response;

import com.guiji.entity.EAnswerType;
import com.guiji.entity.EUserRole;
import com.guiji.entity.EUserState;
import lombok.Data;

@Data
public class QueryUser {
    private Long userId;
    private String userName;
   // private EUserRole userRole;
    private String userPwd;
    private EAnswerType answerType;
    private EUserState userState;
    private String mobile;
    private String createDate;
    private String wssUrl;
    private Long creator;
    private Long queueId;
    private String queueName;

}
