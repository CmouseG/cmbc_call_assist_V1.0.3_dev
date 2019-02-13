package com.guiji.ccmanager.controller;

import com.guiji.calloutserver.api.ICallPlan;
import com.guiji.calloutserver.entity.DispatchPlan;
import com.guiji.component.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
public class TestController2 {

    @Autowired
    ICallPlan iCallPlan;

    @Async
    public void test(String tempId,int line,int userId,String phoneNum){
        log.info("--------start-------");
        DispatchPlan dispatchPlan = new DispatchPlan();
        dispatchPlan.setBatchId(11);
        dispatchPlan.setOrgCode("1");
        Random random = new Random();
        phoneNum = "1515541"+ random.nextInt(9)+ random.nextInt(9)+
                random.nextInt(9)+ random.nextInt(9);
        dispatchPlan.setPhone(phoneNum);
        dispatchPlan.setPlanUuid(UUID.randomUUID().toString().replace("-",""));
        dispatchPlan.setTempId(tempId);
        dispatchPlan.setTts(false);
        dispatchPlan.setUserId(userId);

        Result.ReturnData result = iCallPlan.startMakeCall(dispatchPlan);

        log.info(">>>>>end,result[{}] ",result);
    }

}
