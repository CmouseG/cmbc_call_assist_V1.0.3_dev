package com.guiji.callcenter.dao.entityext;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * author:liyang
 * Date:2019/2/26 10:05
 * Description:
 */
@Data
public class MyCallOutPlanQueryEntity {

    Date startDate;
    Date endDate;

    Integer limitStart;
    Integer limitEnd;

    String phoneNum;

    Integer durationMin;
    Integer durationMax;
    Integer billSecMin;
    Integer billSecMax;

    String accurateIntent;
    List<String> accurateIntentList;

    String reason;
    List<String> reasonList;

    BigInteger callId;

    String tempId;

    Integer isRead;

    Integer customerId;

    //从前端带过来的查询条件
    Integer queryUser;

    Integer intervened;
    Integer batchId;
    Integer lineId;

    Integer orgId;
    List<Integer> orgIdList;
    String agentId;

    List<BigInteger> callIdList;

    List<BigInteger> notInList;
    BigInteger minCallId;

    Integer isdel;
    Integer callStateMin;

    Integer isDesensitization;
    //多少天的数据
    Integer time;

    String answerUser;
    String enterprise;
}



