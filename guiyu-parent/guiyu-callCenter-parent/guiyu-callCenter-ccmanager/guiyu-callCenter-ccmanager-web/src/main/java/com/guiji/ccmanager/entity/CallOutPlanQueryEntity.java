package com.guiji.ccmanager.entity;

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
public class CallOutPlanQueryEntity {

    Date startDate;
    Date endDate;
    String customerId;
    String orgCode;
    String phoneNum;
    String durationMin;
    String durationMax;
    String billSecMin;
    String billSecMax;
    String accurateIntent;
    String freason;
    String callId;
    String tempId;
    String isRead;
    Boolean isSuperAdmin;
    String intervened;
    Integer authLevel;
    Integer batchId;
    Integer callState;
    Integer callStateMin;

    List<BigInteger> notInList;
    List<BigInteger> inList;

}



