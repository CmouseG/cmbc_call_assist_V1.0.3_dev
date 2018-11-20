package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.enm.ECallState;

import java.util.List;

public interface CallOutPlanService {
    void save(CallOutPlan callPlan);
    void save(List<CallOutPlan> callOutPlans);

    CallOutPlan findByCallId(String callId);

    void update(CallOutPlan callplan);
}
