package com.guiji.calloutserver.api;


import com.guiji.common.result.Result;
import feign.Param;
import feign.RequestLine;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("guiyu-callcenter-calloutserver")
public interface ICallPlanApi {
    @ApiOperation(value = "启动客户呼叫计划")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tempId", value = "模板id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lineId", value = "线路id", dataType = "Integer", paramType = "query")
    })
    @RequestLine("GET /startcallplan?customerId={customerId}&tempId={tempId}&lineId={lineId}")
    Result.ReturnData startCallPlan(@Param("customerId") String customerId, @Param("tempId") String tempId, @Param("lineId") Integer lineId);
}
