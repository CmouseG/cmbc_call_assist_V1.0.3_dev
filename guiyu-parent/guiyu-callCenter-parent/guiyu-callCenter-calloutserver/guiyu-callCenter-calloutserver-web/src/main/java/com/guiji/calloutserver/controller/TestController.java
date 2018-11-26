package com.guiji.calloutserver.controller;

import com.google.common.eventbus.AsyncEventBus;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.enm.ECallDirection;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.eventbus.event.CallResourceReadyEvent;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.service.CallOutPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/23 14:21
 * @Project：guiyu-parent
 * @Description:
 */

/**
 * 用于插入测试数据，发起外呼
 */
@Slf4j
@RestController
public class TestController {
    @Autowired
    CallOutPlanService callOutPlanService;

    @Autowired
    EurekaManager eurekaManager;

    @Autowired
    AsyncEventBus asyncEventBus;

    @GetMapping("/testcall")
    public CallOutPlan startTestCall(@RequestParam String number, @RequestParam String temp, @RequestParam Integer lineId){
        log.debug("收到测试呼叫请求, 号码[{}], 模板[{}],线路id[{}]", number, temp, lineId);
        CallOutPlan callOutPlan = new CallOutPlan();
        callOutPlan.setPhoneNum(number);
        callOutPlan.setTempId(temp);
        callOutPlan.setLineId(lineId);
        callOutPlan.setCallId(UUID.randomUUID().toString());
        callOutPlan.setHasTts(false);
        callOutPlan.setServerid(eurekaManager.getInstanceId());
        callOutPlan.setCallState(ECallState.init.ordinal());
        callOutPlan.setCreateTime(new Date());
        callOutPlan.setCallDirection(ECallDirection.OUTBOUND.ordinal());
        callOutPlan.setCustomerId("16");

        callOutPlanService.add(callOutPlan);

        CallResourceReadyEvent event = new CallResourceReadyEvent(callOutPlan);
        asyncEventBus.post(event);

        return callOutPlan;
    }
}
