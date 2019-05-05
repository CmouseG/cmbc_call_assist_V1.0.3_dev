package com.guiji.ccmanager.api;

import com.guiji.ccmanager.entity.LineRateResponse;
import com.guiji.component.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@FeignClient("guiyu-callcenter-ccmanager")
public interface ILineRate {

    @ApiOperation(value = "线路监控信息")
    @GetMapping(value = "/out/getLineRate")
    Result.ReturnData<LineRateResponse> getLineRate(@RequestParam(value = "lineId") Integer lineId,
                                                             @RequestParam(value = "startTime") Date startTime,
                                                             @RequestParam(value = "endTime") Date endTime);

    @ApiOperation(value = "线路监控信息,所有线路")
    @GetMapping(value = "/out/getLineRateAll")
    Result.ReturnData<List<LineRateResponse>> getLineRateAll(@RequestParam(value = "startTime") String startTime,
                                                             @RequestParam(value = "endTime") String endTime)throws Exception;



}
