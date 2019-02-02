package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.CallLineResultMapper;
import com.guiji.callcenter.dao.entity.CallLineResult;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.service.CallLineResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class CallLineResultServiceImpl implements CallLineResultService {

    @Autowired
    CallLineResultMapper callLineAvailableManager;

    @Override
    @Async
    public void addLineResult(CallOutPlan callPlan, boolean successed) {

        CallLineResult callLineResult = new CallLineResult();
        callLineResult.setCallId(callPlan.getCallId());
        callLineResult.setLineId(callPlan.getLineId());
        callLineResult.setSuccessed(successed);
        callLineResult.setCreateTime(new Date());
        callLineAvailableManager.insert(callLineResult);
        log.info("记录线路接通信息 callLineResult[{}]", callLineResult);

    }
}
