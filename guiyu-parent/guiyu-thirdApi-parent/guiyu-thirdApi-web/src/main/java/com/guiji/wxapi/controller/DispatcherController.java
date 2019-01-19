package com.guiji.wxapi.controller;

import com.guiji.component.result.Result;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.dispatch.model.PlanCountVO;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class DispatcherController {

    @Autowired
    IDispatchPlanOut dispatchPlanOut;

    @ApiOperation(value = "获取计划数")
    @GetMapping("5c2f306a2393c")
    public Result.ReturnData<PlanCountVO> getCallCount(@RequestHeader String orgCode) {

        return dispatchPlanOut.getPlanCountByUserId(orgCode);

    }


    @ApiOperation(value = "一键停止拨打,type： 1包括，0 不包括")
    @GetMapping("5c2f30ceb5cde")
    public Result.ReturnData<Boolean> stopCallPlan(@RequestHeader("orgCode") String orgCode,
                                                   @RequestParam("type") @NotEmpty(message = "type不能为空") String type) {

        return dispatchPlanOut.opertationStopPlanByUserId(orgCode,type);
    }




}
