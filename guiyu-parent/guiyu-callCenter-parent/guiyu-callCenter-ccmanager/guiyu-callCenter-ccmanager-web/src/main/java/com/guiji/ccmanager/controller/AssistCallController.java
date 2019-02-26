package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.calloutserver.api.IAssistCall;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.service.AssistCallService;
import com.guiji.component.result.Result;
import com.guiji.utils.FeignBuildUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class AssistCallController {

    @Autowired
    AssistCallService assistCallService;

    @ApiOperation(value = "协呼，转人工")
    @GetMapping("/assistToAgent")
    public Result.ReturnData assistToAgent(String callId,String agentGroupId){

        String server = assistCallService.getServerId(new BigInteger(callId));
        IAssistCall iAssistCall = FeignBuildUtil.feignBuilderTarget(IAssistCall.class, Constant.PROTOCOL + server);
        return iAssistCall.assistToAgent(callId,agentGroupId);
    }

    @ApiOperation(value = "协呼，关闭机器人")
    @GetMapping("/assistCloseRobot")
    public Result.ReturnData assistCloseRobot(String callId){

        String server = assistCallService.getServerId(new BigInteger(callId));
        IAssistCall iAssistCall = FeignBuildUtil.feignBuilderTarget(IAssistCall.class, Constant.PROTOCOL + server);
        return iAssistCall.assistCloseRobot(callId);
    }

    @ApiOperation(value = "协呼，转人工并且关闭机器人")
    @GetMapping("/assistToAgentAndCloseRobot")
    public Result.ReturnData assistToAgentAndCloseRobot(String callId,String agentGroupId){

        String server = assistCallService.getServerId(new BigInteger(callId));
        IAssistCall iAssistCall = FeignBuildUtil.feignBuilderTarget(IAssistCall.class, Constant.PROTOCOL + server);
        return iAssistCall.assistToAgentAndCloseRobot(callId,agentGroupId);
    }

}
