package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;

import java.math.BigInteger;

public interface CallOutPlanService {
    void add(CallOutPlan callPlan);

    CallOutPlan findByCallId(BigInteger callId, Integer orgId);
    CallOutPlan findByPlanUuid(String callId, Integer orgId);

    void update(CallOutPlan callplan);

    int getNotEndCallCount();

    void updateNotOverWriteIntent(CallOutPlan callPlan);

    void updateNotOverWriteCallState(CallOutPlan callPlanUpdate);
}
