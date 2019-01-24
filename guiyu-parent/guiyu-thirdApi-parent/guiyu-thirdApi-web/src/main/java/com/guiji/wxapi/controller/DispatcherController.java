package com.guiji.wxapi.controller;

import com.guiji.component.result.Result;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.dispatch.model.PlanCountVO;
import com.guiji.utils.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class DispatcherController {

    private final Logger logger = LoggerFactory.getLogger(DispatcherController.class);
    @Autowired
    IDispatchPlanOut dispatchPlanOut;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "获取计划数")
    @GetMapping("getCallPlanCount")
    public Result.ReturnData<PlanCountVO> getCallCount(@RequestHeader String orgCode) {
        logger.info("================getCallCount,orgCode[{}]",orgCode);
        if(redisUtil.hasKey("thirdapi-stopCallPlan"+orgCode)){
            return Result.error("0303010");
        }
        return dispatchPlanOut.getPlanCountByUserId(orgCode);

    }


    @ApiOperation(value = "一键停止拨打,type： 1包括，0 不包括")
    @GetMapping("stopCallPlan")
    public Result.ReturnData<Boolean> stopCallPlan(@RequestHeader("orgCode") String orgCode,
                                                   @RequestParam("type") @NotEmpty(message = "type不能为空") String type) {

        logger.info("================stopCallPlan,orgCode[{}],type[{}]",orgCode,type);

        if(redisUtil.hasKey("thirdapi-stopCallPlan"+orgCode)){
            return Result.error("0303010");
        }

        Result.ReturnData<Boolean>  result = dispatchPlanOut.opertationStopPlanByUserId(orgCode,type);
        if(result.success && result.getBody()){
            redisUtil.set("thirdapi-stopCallPlan"+orgCode,"600",600);
        }
        return result;
    }




}
