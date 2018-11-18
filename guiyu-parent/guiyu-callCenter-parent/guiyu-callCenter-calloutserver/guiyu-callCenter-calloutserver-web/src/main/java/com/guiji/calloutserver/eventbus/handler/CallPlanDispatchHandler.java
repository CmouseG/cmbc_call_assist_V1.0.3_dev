package com.guiji.calloutserver.eventbus.handler;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.callcenter.dao.entity.LineCount;
import com.guiji.calloutserver.enm.ECallDirection;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.eventbus.event.AfterCallEvent;
import com.guiji.calloutserver.eventbus.event.CallResourceReadyEvent;
import com.guiji.calloutserver.eventbus.event.StartCallPlanEvent;
import com.guiji.calloutserver.manager.DispatchManager;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.service.*;
import com.guiji.component.result.Result;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.dispatch.model.DispatchPlan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

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
    LineInfoService lineInfoService;

    @Autowired
    LineCountService lineCountService;

    @Autowired
    EurekaManager eurekaManager;

    @Autowired
    CallResourceChecker callResourceChecker;

    @Autowired
    CallService callService;

    @Autowired
    DispatchManager dispatchService;

    @Autowired
    IDispatchPlanOut dispatchPlanOut;

    @Autowired
    AsyncEventBus asyncEventBus;

    /**
     * 启动呼叫计划
     */
    @Subscribe
    public void handleStartCallOutPlanEvent(StartCallPlanEvent event){
        log.debug("收到启动呼叫计划事件[{}]", event);
        LineCount lineCount = event.getLineCount();

        try {
            //调用调度中心的获取客户呼叫计划(请求数=并发数)，获取初始呼叫计划
            Integer requestNum = event.getLineCount().getMaxConcurrentCalls();

            //TODO 这个是一个一个去请求调度中心好呢，还是一次请求多个好呢
            for (int i=1;i<=requestNum;i++) {
                getAvailableSchedules(Integer.valueOf(event.getCustomerId()), lineCount.getLineId());
            }

        }catch (Exception ex){
            log.warn("处理启动计划出现异常", ex);
            //TODO: 报警
        }
    }

    /**
     * 在所有资源(模板录音、tts录音、机器人资源)齐备之后，发起外呼
     * 确切的说，是CallResourceChecker在检查完依赖资源之后，会抛出该CallPlanReadyEvent
     * @param event
     */
    @Subscribe
    public void handleCallResourceReadyEvent(CallResourceReadyEvent event){
        //资源准备好，发起外呼
        CallOutPlan callplan = event.getCallPlan();

        //全局录音文件名称, 必须在一开始就指定，因为呼叫命令中会用到
        String recordFileName = callplan.getCallId() + ".wav";
        callplan.setCallDirection(ECallDirection.OUTBOUND.ordinal());
        callplan.setCallState(ECallState.make_call.ordinal());
        callplan.setCallStartTime(new Date());
        callOutPlanService.save(callplan);

        //保存录音文件信息
        CallOutRecord callOutRecord = new CallOutRecord();
        callOutRecord.setCallId(callplan.getCallId());
        callOutRecord.setRecordFile(recordFileName);
        callOutRecordService.save(callOutRecord);

        callService.makeCall(callplan, callOutRecord);
        //构建外呼命令，发起外呼
    }

    /**
     * 在一通呼叫挂断后，重新拉取呼叫计划
     * @param afterCallEvent
     */
    @Subscribe
    public void handleAfterCallEvent(AfterCallEvent afterCallEvent){
        log.debug("收到AfterCallEvent, 检查是否有待拨打的计划");

        CallOutPlan calloutPlan = afterCallEvent.getCallPlan();

        //挂断后再请求一个呼叫数据，不让线路空闲
        try {
            getAvailableSchedules(Integer.valueOf(calloutPlan.getCustomerId()), calloutPlan.getLineId() );

        } catch (Exception e) {
            log.warn("在挂断后拉取新计划出现异常", e);
            //TODO: 报警
        }
    }

    /**
     * 请求调度中心，拉取呼叫号码， 然后准备呼叫
     * @param customerId
     * @param lineId
     */
    public void getAvailableSchedules(int customerId, int lineId ) {

        Result.ReturnData<List<DispatchPlan>> result = dispatchPlanOut.queryAvailableSchedules(customerId,1,lineId);

        List<DispatchPlan> list =  result.getBody();
        if(list!=null && list.size()>0){
            DispatchPlan dispatchPlan = result.getBody().get(0);
            CallOutPlan callPlan = new CallOutPlan();
            callPlan.setCallId(dispatchPlan.getPlanUuid());
            callPlan.setPhoneNum(dispatchPlan.getPhone());
            callPlan.setCustomerId(String.valueOf(dispatchPlan.getUserId()));
            callPlan.setLineId(dispatchPlan.getLine());
            //TODO CallOutPlan字段是否全

            callPlan.setCallState(ECallState.call_prepare.ordinal());
            callPlan.setCreateTime(new Date());
            callOutPlanService.save(callPlan);
            callResourceChecker.checkCallResources(callPlan);
        }

    }

}
