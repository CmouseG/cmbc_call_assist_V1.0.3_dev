package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.enm.ECallState;

import java.math.BigInteger;
import java.util.List;

public interface CallOutPlanService {
    void add(CallOutPlan callPlan);

    CallOutPlan findByCallId(BigInteger callId);

//    CallOutPlan findByPlanUuid(String planUuid);

    void update(CallOutPlan callplan);
}
