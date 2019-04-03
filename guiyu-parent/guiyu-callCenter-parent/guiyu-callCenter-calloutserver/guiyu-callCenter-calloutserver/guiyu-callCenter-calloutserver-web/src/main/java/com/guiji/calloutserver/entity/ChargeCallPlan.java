package com.guiji.calloutserver.entity;

import lombok.Data;

import java.util.Date;

/**
 * 计费
 */
@Data
public class ChargeCallPlan {

    /**
     * 企业用户ID
     */
    private Integer userId;

    /**
     * 号码
     */
    private String phone;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 通话时长 “秒”为单位
     */
    private Integer billSec;

    private Integer lineId;

}
