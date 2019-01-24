package com.guiji.calloutserver.eventbus.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.eventbus.event.ChannelProgressEvent;
import com.guiji.calloutserver.manager.AIManager;
import com.guiji.calloutserver.manager.DispatchManager;
import com.guiji.calloutserver.service.CallOutPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ChannelProgressHandler {

    @Autowired
    CallOutPlanService callOutPlanService;
    @Autowired
    AsyncEventBus asyncEventBus;
    @Autowired
    AIManager aiManager;
    @Autowired
    DispatchManager dispatchService;

    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    ScheduledExecutorService progressScheduledExecutor = Executors.newScheduledThreadPool(20);

    @Subscribe
    @AllowConcurrentEvents
    public void handleAfterCall(ChannelProgressEvent channelProgressEvent) {

        String callId = channelProgressEvent.getUuid();
        CallOutPlan callOutPlan = callOutPlanService.findByCallId(new BigInteger(callId));
        int callState = callOutPlan.getCallState();

        if(callState >= ECallState.progress.ordinal()){
            return;
        }else{
            CallOutPlan callOutPlanNew = new CallOutPlan();
            callOutPlanNew.setCallState(ECallState.progress.ordinal());
            callOutPlanNew.setCallId(callOutPlan.getCallId());
            callOutPlanService.update(callOutPlanNew);
            log.info("启动定时器，2分钟后检查callId[{}]",callOutPlan.getCallId());
            progressScheduledExecutor.schedule(() -> {

                log.info("2分钟已到，去检查callOutPlan的状态callId[{}]",callOutPlan.getCallId());
                CallOutPlan callOutPlan2Minutes = callOutPlanService.findByCallId(new BigInteger(callId));

                if(callOutPlan2Minutes.getCallState()==ECallState.progress.ordinal()){

                    callOutPlan2Minutes.setCallState(ECallState.hangup_fail.ordinal());
                    callOutPlan2Minutes.setReason("系统通信异常");
                    if (callOutPlan2Minutes.getAccurateIntent() == null){
                        callOutPlan2Minutes.setAccurateIntent("W");
                    }
                    callOutPlanService.update(callOutPlan2Minutes);

                    //释放ai资源
                    log.info("2分钟后，开始释放ai资源,callplanId[{}], aiId[{}]", callOutPlan2Minutes.getCallId(), callOutPlan2Minutes.getAiId());
                    aiManager.releaseAi(callOutPlan2Minutes);
                    log.info("2分钟后，回调调度中心，callId[{}]", callOutPlan2Minutes.getCallId());
                    dispatchService.successSchedule(callOutPlan2Minutes.getPlanUuid(),callOutPlan2Minutes.getPhoneNum(),callOutPlan2Minutes.getAccurateIntent(), callOutPlan2Minutes.getCustomerId(), callOutPlan2Minutes.getLineId(), callOutPlan2Minutes.getTempId());
                }

            }, 2, TimeUnit.MINUTES);


        }



    }

}
