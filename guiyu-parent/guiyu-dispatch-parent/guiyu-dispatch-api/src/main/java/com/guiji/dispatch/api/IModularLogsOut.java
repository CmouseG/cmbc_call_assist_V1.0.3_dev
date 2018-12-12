package com.guiji.dispatch.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.guiji.component.result.Result;
import com.guiji.dispatch.model.ModularLogs;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 模块记录日志
 *
 * @version V1.0
 * @Description:  模块记录日志
 * @author: xujin
 */
@FeignClient("GUIYU-DISPATCH-WEB")
public interface IModularLogsOut {
	@ApiOperation(value = "模块记录日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "notifyLogs", value = "模块记录日志", required = true)
    })
    @PostMapping(value = "out/notifyLogs")
	Result.ReturnData<Boolean> notifyLogs(@RequestBody ModularLogs modularLogs);
	

}
