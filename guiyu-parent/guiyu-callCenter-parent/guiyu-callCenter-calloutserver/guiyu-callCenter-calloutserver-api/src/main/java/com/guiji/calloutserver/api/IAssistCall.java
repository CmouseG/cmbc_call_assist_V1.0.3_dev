package com.guiji.calloutserver.api;

import com.guiji.component.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("guiyu-callcenter-calloutserver")
public interface IAssistCall {

/*    @ApiOperation(value = "转人工")
    @GetMapping("/assistToAgent")
    Result.ReturnData assistToAgent(@RequestParam("callId") String callId,@RequestParam("agentGroupId") String agentGroupId);

    @ApiOperation(value = "关闭机器人")
    @GetMapping("/assistCloseRobot")
    Result.ReturnData assistCloseRobot(@RequestParam("callId") String callId);*/

    @ApiOperation(value = "转人工并且关闭机器人")
    @GetMapping("/assistToAgentAndCloseRobot")
    Result.ReturnData assistToAgentAndCloseRobot(@RequestParam("uuid") String uuid,@RequestParam("agentGroupId") String agentGroupId);
}
