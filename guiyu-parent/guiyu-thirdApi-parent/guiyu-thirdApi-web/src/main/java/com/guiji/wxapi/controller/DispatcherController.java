package com.guiji.wxapi.controller;

import com.guiji.component.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DispatcherController {

    @ApiOperation(value = "获取计划数")
    @GetMapping("5c2f306a2393c")
    public Result.ReturnData<String[]> getCallCount(@RequestHeader Long userId) {
        return null;
    }


    @ApiOperation(value = "一键停止拨打")
    @GetMapping("5c2f30ceb5cde")
    public Result.ReturnData<String[]> stopCallPlan(@RequestHeader Long userId) {
        return null;
    }




}
