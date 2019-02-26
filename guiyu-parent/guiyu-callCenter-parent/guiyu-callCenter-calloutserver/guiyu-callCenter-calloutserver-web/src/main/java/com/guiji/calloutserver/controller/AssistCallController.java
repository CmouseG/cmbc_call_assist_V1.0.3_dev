package com.guiji.calloutserver.controller;


import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.api.IAssistCall;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.entity.AIRequest;
import com.guiji.calloutserver.eventbus.event.AsrCustomerEvent;
import com.guiji.calloutserver.fs.LocalFsServer;
import com.guiji.calloutserver.manager.AIManager;
import com.guiji.calloutserver.manager.ToAgentManager;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.component.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 转人工
     */
    @GetMapping("/assistToAgent")
    public Result.ReturnData assistToAgent(@RequestParam("callId") String callId,@RequestParam("agentGroupId") String agentGroupId){

        log.info("接到转人工请求，callId[{}]",callId);
        BigInteger bigInteCallId = new BigInteger(callId);

        CallOutPlan callPlan = new CallOutPlan();
        callPlan.setCallId(bigInteCallId);
        callPlan.setAgentStartTime(new Date());
        callPlan.setCallState(ECallState.to_agent.ordinal());
        callOutPlanService.update(callPlan);

        String toAgentFs = toAgentManager.findToAgentFsAdder();
        localFsServer.transferToAgentGroup(callId, toAgentFs, agentGroupId);
        return Result.ok();
    }


    /**
     * 关闭机器人
     */
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

    }

}
