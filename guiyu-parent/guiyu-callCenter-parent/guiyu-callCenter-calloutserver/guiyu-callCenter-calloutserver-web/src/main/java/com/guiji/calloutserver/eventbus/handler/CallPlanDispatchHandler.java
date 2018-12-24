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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    AsyncEventBus asyncEventBus;

    @Autowired
    DispatchLogService dispatchLogService;

    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    /**
     * 启动呼叫计划
     */
    @Subscribe
    public void handleStartCallOutPlanEvent(StartCallPlanEvent event){
        log.info("-------------------------收到启动呼叫计划事件[{}]", event);
        LineCount lineCount = event.getLineCount();

        try {
            //调用调度中心的获取客户呼叫计划(请求数=并发数)，获取初始呼叫计划
            Integer requestNum = event.getLineCount().getMaxConcurrentCalls();

            log.info("开始构建[{}]个空白AfterCallEvent事件，驱动后续拨打", requestNum);
            for (int i=1;i<=requestNum;i++) {
                log.info("------ getAvailableSchedules:"+lineCount.getLineId()+"  ," + i);
//                getAvailableSchedules(Integer.valueOf(event.getCustomerId()), lineCount.getLineId(), event.getTempId());
                CallOutPlan callplan = new CallOutPlan();
                callplan.setCustomerId(event.getCustomerId());
                callplan.setLineId(lineCount.getLineId());
                callplan.setTempId(event.getTempId());
                AfterCallEvent afterCallEvent = new AfterCallEvent(callplan,true);
                asyncEventBus.post(afterCallEvent);
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
        log.info("----------- CallResourceReadyEvent"+event.getCallPlan().getPhoneNum());
        //资源准备好，发起外呼
        CallOutPlan callplan = event.getCallPlan();

        //全局录音文件名称, 必须在一开始就指定，因为呼叫命令中会用到
        String recordFileName = callplan.getCallId() + ".wav";
        callplan.setCallDirection(ECallDirection.OUTBOUND.ordinal());
        if(callplan.getCallState()==null || callplan.getCallState()<ECallState.make_call.ordinal()){
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

    /**
     * 在一通呼叫挂断后，重新拉取呼叫计划
     * @param afterCallEvent
     */
    @Subscribe
    public void handleAfterCallEvent(AfterCallEvent afterCallEvent){
        log.info("收到AfterCallEvent[{}], 检查是否有待拨打的计划", afterCallEvent);

        CallOutPlan calloutPlan = afterCallEvent.getCallPlan();

        //挂断后再请求一个呼叫数据，不让线路空闲
        try {
            List<CallOutPlan> list = dispatchService.pullCallPlan(Integer.valueOf(calloutPlan.getCustomerId()),1,calloutPlan.getLineId());
            log.info("----------- getAvailableSchedules callPlan list size [{}] ", list.size());
            if(list!=null && list.size()>0){
                CallOutPlan callPlan= list.get(0);
                callPlan.setCallState(ECallState.call_prepare.ordinal());
                callPlan.setCreateTime(new Date());
                callPlan.setIsdel(0);
                callPlan.setIsread(0);
                log.info("----------- getAvailableSchedules callPlan [{}] ", callPlan);

                callOutPlanService.add(callPlan);

                try {
//            如果申请机器人异常 ，回掉失败给调度中心,下次还可以拉取该次通话
//              callResourceChecker.checkCallResources(callPlan);
//                    callResourceChecker.checkTemp(callPlan.getTempId());
//        checkTts(callOutPlan);
                    log.info("开始检查机器人资源");
                    callResourceChecker.checkSellbot(callPlan);
                }catch (NullPointerException e){
                    //回掉给调度中心，更改通话记录
                    callPlan.setCallState(ECallState.norobot_fail.ordinal());
                    callPlan.setAccurateIntent("W");
                    callPlan.setReason(e.getMessage());
                    callOutPlanService.update(callPlan);
                    dispatchService.successSchedule(callPlan.getCallId(),callPlan.getPhoneNum(),"W");
                    return;
                }
                asyncEventBus.post(new CallResourceReadyEvent(callPlan));
                log.info("---------------------CallResourceReadyEvent post "+callPlan.getPhoneNum());
            }
        } catch (Exception e) {
            log.warn("在挂断后拉取新计划出现异常", e);
            //这里会出一个异常，比如重复的id,需要重新发一个事件出来，不然会少一路并发数
            AfterCallEvent afterCallEventAgain = new AfterCallEvent(afterCallEvent.getCallPlan(),true);
            asyncEventBus.post(afterCallEventAgain);
        }
    }


}
