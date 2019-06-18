package com.guiji.calloutserver.service;

import org.springframework.scheduling.annotation.Async;

/**
 * author:liyang
 * Date:2019/5/21 16:39
 * Description:
 */
public interface SimLimitService {

    @Async
    void addSimCall(String callId, Integer lineId, Integer billSec);

    void addSimCall(Boolean isSimcall, Integer lineId, Integer billSec);

    Boolean isAllowSimCall(Integer lineId);

    void changeSimLineConfig(Integer lineId, Boolean callCountPeriodChange, Boolean connectCountPeriodChange, Boolean connectTimePeriodChange);
}
