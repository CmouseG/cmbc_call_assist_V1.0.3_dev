package com.guiji.ccmanager.vo;

import lombok.Data;

/**
 * author:liyang
 * Date:2019/2/26 9:51
 * Description:
 */
@Data
public class CallRecordListReq {

    String startDate;
    String endDate;
    String pageSize;
    String pageNo;
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


}
