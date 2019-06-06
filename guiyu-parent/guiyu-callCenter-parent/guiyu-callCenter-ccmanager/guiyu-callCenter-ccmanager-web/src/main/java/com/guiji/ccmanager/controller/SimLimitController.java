package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.LineSimlimitConfig;
import com.guiji.ccmanager.service.SimLimitService;
import com.guiji.ccmanager.vo.SimLimitConfigReq;
import com.guiji.component.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * author:liyang
 * Date:2019/5/30 9:35
 * Description:
 */
@Validated
@RestController
@Slf4j
public class SimLimitController {


    @Autowired
    SimLimitService simLimitService;

    @ApiOperation(value = "查询sim卡拨打限制信息。0:10分钟、1:20分钟、2:30分钟、3:1小时、4:1天、5:1个月")
    @GetMapping(value = "getSimLimitConfigInfo")
    public Result.ReturnData<LineSimlimitConfig> getSimLimitConfigInfo(@NotNull(message = "lineId不能为空") Integer lineId){

        log.info("getSimLimitConfigInfo lineId[{}]",lineId);
        return Result.ok(simLimitService.getSimLimitConfigInfo(lineId));

    }

    @ApiOperation(value = "修改sim卡拨打限制信息。")
    @PostMapping(value = "updateSimLimitConfigInfo")
    public Result.ReturnData updateSimLimitConfigInfo(@RequestBody @Validated SimLimitConfigReq simLimitConfigReq){

        log.info("updateSimLimitConfigInfo simLimitConfigReq[{}]",simLimitConfigReq);
        if(simLimitService.updateSimLimitConfigInfo(simLimitConfigReq)){
            return Result.ok();
        }else{
            return Result.error("0303024");
        }
    }

}
