package com.guiji.fsagent.api;

import com.guiji.common.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient("guiyu-callcenter-fsagent")
public interface LineOperateApi {
    @ApiOperation(value = "配置文件更新通知接口 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "请求类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lineId", value = "线路Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="updatenotify")
    public Result.ReturnData<Boolean> updatenotify(String type, String lineId);

    @ApiOperation(value = "删除线路接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineId", value = "线路Id", dataType = "String", paramType = "query")
    })
    @DeleteMapping(value="/lineinfos/{lineId}")
    public Result.ReturnData<Boolean>  deleteLineinfos(@PathVariable String lineId);

}
