package com.guiji.ccmanager.api;

import com.guiji.component.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

@FeignClient("guiyu-callcenter-ccmanager")
public interface IReportLine {

    @ApiOperation(value = "线路监控信息")
    @GetMapping(value = "getLineMonitorReport")
    Result.ReturnData getLineMonitorReport(@RequestParam(value = "lineId") String lineId, @RequestParam(value = "dimension") String dimension,
                                           @RequestParam(value = "orgCode") String orgCode, @RequestParam(value = "userId") Long userId,
                                           @RequestParam(value = "authLevel") Integer authLevel);


    @ApiOperation(value = "线路错误信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getLineHangupDetail")
    Result.ReturnData getLineHangupDetail(@RequestParam(value="lineId") String lineId,@RequestParam(value="startTime") String startTime,
                                          @RequestParam(value="enTime") String enTime, @RequestParam(value="orgCode")  String orgCode,
                                          @RequestParam(value = "userId") Integer userId,@RequestParam(value = "authLevel") Integer authLevel) throws ParseException;
}
