package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;

import java.math.BigInteger;

public interface CallOutPlanService {
    void add(CallOutPlan callPlan);

    CallOutPlan findByCallId(BigInteger callId);
    CallOutPlan findByPlanUuid(String callId);

    void update(CallOutPlan callplan);

    int getNotEndCallCount();
}
