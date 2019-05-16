package com.guiji.calloutserver.controller;


import com.google.common.eventbus.AsyncEventBus;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.api.IAssistCall;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.entity.AIRequest;
import com.guiji.calloutserver.entity.Channel;
import com.guiji.calloutserver.eventbus.event.AsrCustomerEvent;
import com.guiji.calloutserver.eventbus.event.ToAgentEvent;
import com.guiji.calloutserver.fs.LocalFsServer;
import com.guiji.calloutserver.helper.ChannelHelper;
import com.guiji.calloutserver.helper.RobotNextHelper;
import com.guiji.calloutserver.manager.AIManager;
import com.guiji.calloutserver.manager.ToAgentManager;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.calloutserver.service.ChannelService;
import com.guiji.component.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Date;

@Slf4j
@RestController
public class AssistCallController implements IAssistCall {

    @Autowired
    LocalFsServer localFsServer;
    @Autowired
    ToAgentManager toAgentManager;
    @Autowired
    CallOutPlanService callOutPlanService;
    @Autowired
    AIManager aiManager;
    @Autowired
    RobotNextHelper robotNextHelper;
    @Autowired
    AsyncEventBus asyncEventBus;
    @Autowired
    ChannelHelper channelHelper;
    @Autowired
    ChannelService channelService;
    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }


/*    *//**
     * 协呼  废弃
     *//*
    @GetMapping("/assistToAgent")
    public Result.ReturnData assistToAgent(@RequestParam("callId") String callId,@RequestParam("agentGroupId") String agentGroupId){

        log.info("接到转人工请求，callId[{}],agentGroupId[{}]",callId,agentGroupId);
        BigInteger bigInteCallId = new BigInteger(callId);

        CallOutPlan callPlan = new CallOutPlan();
        callPlan.setCallId(bigInteCallId);
        callPlan.setAgentStartTime(new Date());
        callPlan.setCallState(ECallState.to_agent.ordinal());
        callPlan.setIntervened(true); //已介入
        callOutPlanService.update(callPlan);

        String toAgentFs = toAgentManager.findToAgentFsAdder();
        localFsServer.transferToAgentGroup(callId, toAgentFs, agentGroupId);
        return Result.ok();
    }


    *//**
     * 关闭机器人  废弃
     *//*
    @GetMapping("/assistCloseRobot")
    public Result.ReturnData assistCloseRobot(@RequestParam("callId") String callId){

        log.info("接到关闭机器人请求，callId[{}]",callId);
        AsrCustomerEvent event = new AsrCustomerEvent();
        event.setUuid(callId);
        event.setAsrText("转人工");
        AIRequest aiRequest = new AIRequest(event);

        try {
            aiManager.sendAiRequest(aiRequest);
            return Result.ok();
        } catch (Exception e) {
            log.error("关闭机器人出现异常",e);
            return Result.error(Constant.ERROR_CALLCOUNT_FAILED);
        }

    }*/

    @GetMapping("/assistToAgentAndCloseRobot")
    public Result.ReturnData assistToAgentAndCloseRobot(@RequestParam("uuid") String uuid,@RequestParam("agentGroupId") String agentGroupId){

        log.info("接到assistToAgentAndCloseRobot请求，uuid[{}],agentGroupId[{}]",uuid,agentGroupId);

        String callId;
        Integer orgId;
        BigInteger bigInteCallId = null;
        try {
            String[] arr = uuid.split(Constant.UUID_SEPARATE);
            callId = arr[0];
            orgId = Integer.valueOf(arr[1]);
            bigInteCallId = new BigInteger(callId);
        }catch (Exception e){
            return Result.error("0305016");
        }

        CallOutPlan realCallPlan = callOutPlanService.findByCallId(bigInteCallId, orgId);
        if(realCallPlan.getCallState()>=ECallState.hangup_ok.ordinal()){
            return Result.error("0305015");
        }

        log.info("开始判定是否需要等待，转协呼,uuid[{}]",uuid);
        Channel channel = channelService.findByUuid(uuid);
        if (channel != null && channel.isInPlay() && channel.getExpectEndPlayTime() != null) {
            long millNow = new Date().getTime();
            if (channel.getExpectEndPlayTime() > millNow) {
                channel.setIsMediaLock(true); //通道锁住，不允许再播放其他录音
                long watiMill = channel.getExpectEndPlayTime() - millNow;
                log.info("将等待[{}]毫秒后再转协呼,uuid[{}]",watiMill,uuid);
                try {
                    Thread.sleep(watiMill);
                } catch (InterruptedException e) {
                    log.error("线程sleep出现异常",e);
                }
            }
        }

        log.info("时间已到，开始转协呼,uuid[{}]",uuid);
        CallOutPlan callPlan = new CallOutPlan();
        callPlan.setCallId(bigInteCallId);
        callPlan.setAgentStartTime(new Date());
        callPlan.setCallState(ECallState.to_agent.ordinal());
        callPlan.setIntervened(true); //已介入
        callPlan.setOrgId(orgId);
        callOutPlanService.update(callPlan);

        String toAgentFs = toAgentManager.findToAgentFsAdder();
        localFsServer.transferToAgentGroup(uuid, toAgentFs, agentGroupId);

        //如果已经进入挂断操作，则停止挂断
        channelHelper.stopKillChannel(uuid);

//        log.info("协呼之后，释放ai资源");

//        aiManager.releaseAi(realCallPlan);

        //停止定时任务
        robotNextHelper.stopAiCallNextTimer(uuid);
        //构建事件抛出
        ToAgentEvent toAgentEvent = new ToAgentEvent(uuid);
        asyncEventBus.post(toAgentEvent);

        return Result.ok();

    }

}
