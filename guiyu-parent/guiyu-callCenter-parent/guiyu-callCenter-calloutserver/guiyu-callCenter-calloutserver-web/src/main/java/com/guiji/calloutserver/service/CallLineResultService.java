package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import org.springframework.scheduling.annotation.Async;

import java.math.BigInteger;

public interface CallLineResultService {
    @Async
    void addLineResult(CallOutPlan callPlan, boolean successed);
}
