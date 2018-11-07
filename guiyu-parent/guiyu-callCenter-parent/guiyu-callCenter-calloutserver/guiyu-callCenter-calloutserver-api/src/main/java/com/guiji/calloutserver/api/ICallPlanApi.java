package com.guiji.calloutserver.api;



import com.guiji.component.result.Result;
import feign.Param;
import feign.RequestLine;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("guiyu-callcenter-calloutserver")
public interface ICallPlanApi {
    @ApiOperation(value = "启动客户呼叫计划")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tempId", value = "模板id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lineId", value = "线路id", dataType = "Integer", paramType = "query")
    })
    @GetMapping("/startcallplan")
    Result.ReturnData startCallPlan(@RequestParam("customerId") String customerId, @RequestParam("tempId") String tempId, @RequestParam("lineId") Integer lineId);
}
