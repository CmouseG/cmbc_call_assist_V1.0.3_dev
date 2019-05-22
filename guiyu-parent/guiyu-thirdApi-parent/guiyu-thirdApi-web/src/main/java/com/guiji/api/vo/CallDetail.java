package com.guiji.api.vo;

import lombok.Data;

/**
 * 通过批次号查询该批次的通话列表，具体通话详情
 */
@Data
public class CallDetail {

    private Long callStartTime;

    private Long hangupTime;

    private Long answerTime;

    private Integer duration;

    private Integer billSec;

    private String endTime;

    private String handupTime;

    private String addDate;

    private String callTime;

    private Integer hangupDirection;

    private String accurateIntent;

    private String reason;

    private String remarks;

    private String freason;

    private String detailList;
}
