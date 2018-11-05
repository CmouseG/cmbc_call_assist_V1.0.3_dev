package com.guiji.fsagent.api;

import com.guiji.component.result.Result;
import com.guiji.fsagent.entity.FsInfoVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

public interface IFsStateApi {
    @ApiOperation(value = "检查服务健康状态")
    @GetMapping(value="ishealthy")
    public Result.ReturnData<Boolean> ishealthy();

    @ApiOperation(value = "检查服务健康状态")
    @GetMapping(value="fsinfo")
    public Result.ReturnData<FsInfoVO> fsinfo();
}
