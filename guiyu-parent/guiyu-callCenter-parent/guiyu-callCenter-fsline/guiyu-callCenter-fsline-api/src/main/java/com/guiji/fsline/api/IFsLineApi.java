package com.guiji.fsline.api;

import com.guiji.common.result.Result;
import feign.RequestLine;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("guiyu-callcenter-fsline")
public interface IFsLineApi {
    @ApiOperation(value = "获取fsline基本信息")
    @RequestLine("GET /fsinfo")
    Result.ReturnData getFsInfo();
}
