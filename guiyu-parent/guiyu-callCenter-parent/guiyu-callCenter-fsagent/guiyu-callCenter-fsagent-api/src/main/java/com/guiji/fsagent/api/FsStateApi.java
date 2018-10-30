package com.guiji.fsagent.api;

import com.guiji.common.result.Result;
import com.guiji.fsagent.entity.FsInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

public interface FsStateApi {
    @ApiOperation(value = "检查服务健康状态")
    @GetMapping(value="ishealthy")
    public Result.ReturnData<Boolean> ishealthy();

    @ApiOperation(value = "检查服务健康状态")
    @GetMapping(value="fsinfo")
    public Result.ReturnData<FsInfo> fsinfo();
}
