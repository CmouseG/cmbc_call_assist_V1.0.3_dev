package com.guiji.calloutserver.api;

import com.guiji.calloutserver.entity.CallEndIntent;
import com.guiji.calloutserver.entity.DispatchPlan;
import com.guiji.component.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("guiyu-callcenter-calloutserver")
public interface ICallPlan {

    @ApiOperation(value = "开始拨打电话")
    @PostMapping("/startMakeCall")
    Result.ReturnData startMakeCall(@RequestBody DispatchPlan dispatchPlan);

    @ApiOperation(value = "查询电话是否拨打结束")
    @GetMapping("/queryCallState")
    Result.ReturnData<CallEndIntent> isCallEnd(@RequestParam(value = "planUuid", required = true) String planUuid,
                                               @RequestParam(value = "orgId", required = true) Integer orgId);

/*    @ApiOperation(value = "查询当前未打完的电话数量")
    @GetMapping("/getNotEndCallCount")
    Result.ReturnData<Integer> getNotEndCallCount();*/

/*    @ApiOperation(value = "模板是否准备好，呼叫中心内部使用，外部勿用")
    @PostMapping("/isTempOk")
    Result.ReturnData<Map<String, Boolean>> isTempOk(@RequestBody List<String> tempIdList);*/
}
