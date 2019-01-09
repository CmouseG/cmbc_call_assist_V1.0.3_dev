package com.guiji.calloutserver.eventbus.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.calloutserver.enm.ECallDirection;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.eventbus.event.AfterCallEvent;
import com.guiji.calloutserver.eventbus.event.CallResourceReadyEvent;
import com.guiji.calloutserver.manager.DispatchManager;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.calloutserver.service.CallOutRecordService;
import com.guiji.calloutserver.service.CallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/1 19:44
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallPlanDispatchHandler {
    @Autowired
    CallOutPlanService callOutPlanService;
    @Autowired
    CallOutRecordService callOutRecordService;
    @Autowired
    CallResourceChecker callResourceChecker;
    @Autowired
    CallService callService;
    @Autowired
    DispatchManager dispatchService;
    @Autowired
    AsyncEventBus asyncEventBus;

    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }


    /**
     * 在所有资源(模板录音、tts录音、机器人资源)齐备之后，发起外呼
     * 确切的说，是CallResourceChecker在检查完依赖资源之后，会抛出该CallPlanReadyEvent
     *
     * @param event
     */
    @Subscribe
    @AllowConcurrentEvents
    public void handleCallResourceReadyEvent(CallResourceReadyEvent event) {
        log.info("----------- CallResourceReadyEvent" + event.getCallPlan().getPhoneNum());
        //资源准备好，发起外呼
        CallOutPlan callplan = event.getCallPlan();

        //全局录音文件名称, 必须在一开始就指定，因为呼叫命令中会用到
        String recordFileName = callplan.getCallId() + ".wav";
        callplan.setCallDirection(ECallDirection.OUTBOUND.ordinal());
        if (callplan.getCallState() == null || callplan.getCallState() < ECallState.make_call.ordinal()) {
            callplan.setCallState(ECallState.make_call.ordinal());
        }
        callplan.setCallStartTime(new Date());
        callOutPlanService.update(callplan);

        //保存录音文件信息
        CallOutRecord callOutRecord = new CallOutRecord();
        callOutRecord.setCallId(callplan.getCallId());
        callOutRecord.setRecordFile(recordFileName);
        callOutRecordService.save(callOutRecord);

        callService.makeCall(callplan, callOutRecord);
        //构建外呼命令，发起外呼
    }

    @Subscribe
    @AllowConcurrentEvents
    public void successSchedule(AfterCallEvent afterCallEvent) {

        CallOutPlan callPlan = afterCallEvent.getCallPlan();
        log.info("拨打结束，回调调度中心，callId[{}]", callPlan.getCallId());
        dispatchService.successSchedule(callPlan.getPlanUuid(),callPlan.getPhoneNum(),callPlan.getAccurateIntent());

    }


    /**
     * 准备发起呼叫
     */
    @Async
    public void readyToMakeCall(CallOutPlan callPlan) {
        log.info("----------- getAvailableSchedules readyToMakeCall callPlan [{}] ", callPlan);

        callPlan.setCallState(ECallState.call_prepare.ordinal());
        callPlan.setCreateTime(new Date());
        callPlan.setIsdel(0);
        callPlan.setIsread(0);
        callPlan.setBillSec(0);
        callPlan.setDuration(0);

        callOutPlanService.add(callPlan);

        try {
            log.info("开始检查机器人资源");
            callResourceChecker.checkSellbot(callPlan);
        } catch (NullPointerException e) {
            //回掉给调度中心，更改通话记录
            //没有机器人资源，会少一路并发数，直接return了
            log.warn("checkSellbot，检查机器人资源失败 callPlan[{}]", callPlan);
            callPlan.setCallState(ECallState.norobot_fail.ordinal());
            callPlan.setAccurateIntent("W");
            callPlan.setReason(e.getMessage());
            callOutPlanService.update(callPlan);

            dispatchService.successSchedule(callPlan.getPlanUuid(), callPlan.getPhoneNum(), "W");
            return;
        }

        asyncEventBus.post(new CallResourceReadyEvent(callPlan));
        log.info("--------------CallResourceReadyEvent post " + callPlan.getCallId());

    }

}
