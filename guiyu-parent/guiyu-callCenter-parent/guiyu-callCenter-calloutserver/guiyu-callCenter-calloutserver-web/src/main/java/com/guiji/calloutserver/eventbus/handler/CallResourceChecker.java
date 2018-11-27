package com.guiji.calloutserver.eventbus.handler;

import com.google.common.eventbus.AsyncEventBus;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.eventbus.event.CallResourceReadyEvent;
import com.guiji.calloutserver.manager.FsAgentManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/2 14:56
 * @Project：guiyu-parent
 * @Description: 用于检查呼叫相关的资源是否齐备，如模板、tts、sellbot等
 */
@Slf4j
@Component
public class CallResourceChecker {
    @Autowired
    AsyncEventBus asyncEventBus;
    @Autowired
    FsAgentManager fsAgentManager;

    /**
     * 检查呼叫依赖的各项资源是否齐备，只有完全齐备，才允许进行后面的呼叫
     * @param callOutPlan
     */
    public void checkCallResources(CallOutPlan callOutPlan){
        //启动多个线程，用于检测各个资源的状态
        checkTemp(callOutPlan);
        checkTts(callOutPlan);
        checkSellbot(callOutPlan);

        asyncEventBus.post(new CallResourceReadyEvent(callOutPlan));
        log.info("---------------------CallResourceReadyEvent post "+callOutPlan.getPhoneNum());
    }

    /**
     * 检查sellbot资源是否就位
     * @param callOutPlan
     */
    private void checkSellbot(CallOutPlan callOutPlan) {
        //TODO:sellbot资源检查
    }

    /**
     * 检查tts是否就位
     * @param callOutPlan
     */
    private void checkTts(CallOutPlan callOutPlan) {
        //TODO:tts资源检查
    }

    /**
     * 检查模板是否就位
     * @param callOutPlan
     */
    private void checkTemp(CallOutPlan callOutPlan) {
        fsAgentManager.istempexist(callOutPlan.getTempId());
        fsAgentManager.getwavlength(callOutPlan.getTempId());
    }
}
