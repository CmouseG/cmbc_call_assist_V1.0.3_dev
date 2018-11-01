package com.guiji.fsline.api;

import com.guiji.common.result.Result;
import com.guiji.fsline.entity.FsLineVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("guiyu-callcenter-fsline")
public interface IFsLine {
    @ApiOperation(value = "获取fsline基本信息")
    @GetMapping("/fsinfo")
    Result.ReturnData<FsLineVO> getFsInfo();
}
