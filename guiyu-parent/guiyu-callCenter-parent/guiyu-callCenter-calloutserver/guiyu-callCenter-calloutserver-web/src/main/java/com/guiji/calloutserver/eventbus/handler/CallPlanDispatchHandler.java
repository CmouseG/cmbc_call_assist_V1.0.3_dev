package com.guiji.calloutserver.eventbus.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.enm.ECallDirection;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.eventbus.event.AfterCallEvent;
import com.guiji.calloutserver.eventbus.event.CallResourceReadyEvent;
import com.guiji.calloutserver.manager.*;
import com.guiji.calloutserver.service.*;
import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.model.SimLineStatus;
import com.guiji.component.result.Result;
import com.guiji.dict.api.ISysDict;
import com.guiji.dict.vo.SysDictVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    @Autowired
    CallingCountManager callingCountManager;
    @Autowired
    LineListManager lineListManager;
    @Autowired
    FsAgentManager fsAgentManager;
    @Autowired
    LineCountWService lineCountWService;
    @Autowired
    StatisticReportHandler statisticReportHandler;
    @Autowired
    AIManager aiManager;
    @Autowired
    SimCallManager simCallManager;
    @Autowired
    ISysDict iSysDict;
    @Autowired
    LineMarketRemote lineMarketRemote;
    @Autowired
    SimLimitService simLimitService;


    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    private ExecutorService executor = Executors.newCachedThreadPool() ;

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
        String recordFileName = callplan.getCallId()+Constant.UUID_SEPARATE+callplan.getOrgId() + ".wav";
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

        //构建外呼命令，发起外呼
        callService.makeCall(callplan, recordFileName);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void successSchedule(AfterCallEvent afterCallEvent) {

        CallOutPlan callPlan = afterCallEvent.getCallPlan();
        log.info("拨打结束，回调调度中心，callId[{}]", callPlan.getCallId());
        if(simCallManager.isSimCall(callPlan.getCallId().toString())){
            try {
                Result.ReturnData<List<SysDictVO>> returnData = iSysDict.getDictValueByTypeKey("simcall_time","simcall_time");
                String value = returnData.getBody().get(0).getDictValue();
                Thread.sleep(Integer.valueOf(value)*1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //重新查询一次，意向标签可能不准确
        CallOutPlan realCallOutPlan = callOutPlanService.findByCallId(callPlan.getCallId(), callPlan.getOrgId());
        dispatchService.successSchedule(realCallOutPlan.getPlanUuid(),realCallOutPlan.getPhoneNum(),realCallOutPlan.getAccurateIntent(),
                realCallOutPlan.getCustomerId(), realCallOutPlan.getLineId(), realCallOutPlan.getTempId(), true);

    }


    /**
     * 准备发起呼叫
     */
//    @Async
    public void readyToMakeCall(CallOutPlan callPlan, List<Integer> lineList, Boolean simCall) {

        executor.submit(new Runnable(){
            @Override
            public void run() {

                log.info("----------- getAvailableSchedules readyToMakeCall callPlan [{}] ", callPlan);

                callPlan.setCallState(ECallState.call_prepare.ordinal());
                callPlan.setCreateTime(new Date());
                callPlan.setIsdel(0);
                callPlan.setIsread(0);
                callPlan.setBillSec(0);
                callPlan.setDuration(0);
                callPlan.setIsAnswer(0);
                callPlan.setIsCancel(0);//是否超时
                callPlan.setTalkNum(0);
                callPlan.setIntervened(false);

                if(simCall!=null && simCall){
                    //判断拨打是否超限
                    Boolean allowSimCall = simLimitService.isAllowSimCall(callPlan.getLineId());
                    if(!allowSimCall){
                        log.info("sim卡超限，不允许拨打,planUuid[{}]",callPlan.getPlanUuid());
                        dispatchService.successScheduleSim(callPlan.getPlanUuid(), callPlan.getPhoneNum(), null, callPlan.getCustomerId(),
                                callPlan.getLineId(), callPlan.getTempId(), true, null, false);
                        return;
                    }
                }

                try {
                    callOutPlanService.add(callPlan);
                    callingCountManager.addOneCall();
                } catch (Exception ex) {
                    log.error("插入calloutplan异常,重复的uuid", ex);
                    //重复的id，回调
                    dispatchService.successSchedule(callPlan.getPlanUuid(), callPlan.getPhoneNum(), null,
                            callPlan.getCustomerId(), callPlan.getLineId(), callPlan.getTempId(), true);
                    callingCountManager.removeOneCall();
                    return;
                }
                try {
                    log.warn("{},{},{},{},{}", callPlan.getPhoneNum(), com.guiji.utils.DateUtil.formatDatetime(new Date()),
                            Constant.MODULE_CALLOUTSERVER, "申请机器人开始", callPlan.getCallId());
                    callResourceChecker.checkSellbot(callPlan);
                    log.warn("{},{},{},{},{}", callPlan.getPhoneNum(), com.guiji.utils.DateUtil.formatDatetime(new Date()),
                            Constant.MODULE_CALLOUTSERVER, "申请机器人成功", callPlan.getCallId());
                } catch (NullPointerException e) {
                    //回掉给调度中心，更改通话记录
                    //没有机器人资源，会少一路并发数，直接return了
                    log.warn("{},{},{},{},{}", callPlan.getPhoneNum(), com.guiji.utils.DateUtil.formatDatetime(new Date()),
                            Constant.MODULE_CALLOUTSERVER, "申请机器人失败", callPlan.getCallId());
                    readyFail(callPlan,"605",false,simCall);
                    return;
                }

                //下载tts语音合成文件 todo 并发是否会有问题
                try {
                    log.warn("{},{},{},{},{}", callPlan.getPhoneNum(), com.guiji.utils.DateUtil.formatDatetime(new Date()),
                            Constant.MODULE_CALLOUTSERVER, "下载tts语音开始", callPlan.getCallId());
                    fsAgentManager.downloadTtsWav(callPlan.getTempId(), callPlan.getPlanUuid(), callPlan.getCallId().toString());
                    log.warn("{},{},{},{},{}", callPlan.getPhoneNum(), com.guiji.utils.DateUtil.formatDatetime(new Date()),
                            Constant.MODULE_CALLOUTSERVER, "下载tts语音成功", callPlan.getCallId());
                }catch (Exception e){
                    log.warn("{},{},{},{},{}", callPlan.getPhoneNum(), com.guiji.utils.DateUtil.formatDatetime(new Date()),
                            Constant.MODULE_CALLOUTSERVER, "下载tts语音失败", callPlan.getCallId());
                    log.error("downloadTtsWav，下载tts语音，出现异常 callid{}", callPlan.getCallId(), e);
                    readyFail(callPlan,"608",true,simCall);
                    //释放机器人
                    aiManager.releaseAi(callPlan);
                    return;
                }

                try {
                    //存储sim卡状态
                    simCallManager.addSimCall(callPlan.getCallId().toString(), simCall);
                }catch (Exception e){
                    log.error("addSimCall出现异常",e);
                }

                asyncEventBus.post(new CallResourceReadyEvent(callPlan));
                log.info("--------------CallResourceReadyEvent post " + callPlan.getCallId());

                //将lineList 存储到到redis中
                lineListManager.addLineList(callPlan.getCallId().toString(),lineList);

            }

        });

    }

    public void readyFail(CallOutPlan callPlan,String reason,Boolean isNeedPlan,Boolean isSimCall){
        callPlan.setCallState(ECallState.norobot_fail.ordinal());
        callPlan.setAccurateIntent("W");
        callPlan.setReason(reason);
        callOutPlanService.update(callPlan);
        dispatchService.successSchedule(callPlan.getPlanUuid(), callPlan.getPhoneNum(), "W",
                callPlan.getCustomerId(), callPlan.getLineId(), callPlan.getTempId(), isNeedPlan);
//        callingCountManager.removeOneCall();
        lineCountWService.addWCount(callPlan.getLineId(),callPlan.getOrgCode(),callPlan.getCustomerId());

        //记录sim卡拨打次数，防止sim卡拨打超限
        simLimitService.addSimCall(isSimCall,callPlan.getLineId(),0);

    }


}
