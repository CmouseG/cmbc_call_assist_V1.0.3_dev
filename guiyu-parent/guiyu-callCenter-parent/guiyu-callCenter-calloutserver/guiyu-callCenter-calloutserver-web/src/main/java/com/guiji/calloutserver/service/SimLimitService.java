package com.guiji.calloutserver.service;

/**
 * author:liyang
 * Date:2019/5/21 16:39
 * Description:
 */
public interface SimLimitService {
    void addSimCall(String callId, Integer lineId, Integer billSec);

    Boolean isAllowSimCall(Integer lineId);

    void changeSimLineConfig(Integer lineId, Boolean callCountPeriodChange, Boolean connectCountPeriodChange, Boolean connectTimePeriodChange);
}
