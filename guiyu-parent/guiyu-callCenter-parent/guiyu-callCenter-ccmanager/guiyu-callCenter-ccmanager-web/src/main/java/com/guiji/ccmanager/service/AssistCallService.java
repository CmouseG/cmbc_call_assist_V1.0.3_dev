package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;

import java.math.BigInteger;

public interface AssistCallService {
    CallOutPlan getCallOutplan(BigInteger callId, Integer orgId);
}
