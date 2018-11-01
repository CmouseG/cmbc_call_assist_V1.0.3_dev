package com.guiji.ccmanager.api;

import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.common.result.Result;
import feign.Param;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/30 0030 09:25
 * @Description:
 */
@FeignClient("guiyu-callcenter-ccmanager")
public interface ICallManagerOutApi {

    @ApiOperation(value = "获取客户线路列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="out/lineinfos")
    public Result.ReturnData<List<LineConcurrent>> outLineinfos(@RequestParam("customerId") String customerId);


    @ApiOperation(value = "启动客户呼叫计划")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tempId", value = "模板id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lineId", value = "线路id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="out/startcallplan")
    public Result.ReturnData<Boolean> startcallplan(@RequestParam("customerId") String customerId, @RequestParam("tempId") String tempId, @RequestParam("lineId") String lineId);

}
