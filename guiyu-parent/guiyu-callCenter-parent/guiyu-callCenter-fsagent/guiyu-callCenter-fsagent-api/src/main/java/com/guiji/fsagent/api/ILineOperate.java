package com.guiji.fsagent.api;

import com.guiji.component.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("guiyu-callcenter-fsagent")
public interface ILineOperate {
    @ApiOperation(value = "配置文件更新通知接口 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "请求类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lineId", value = "线路Id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/updatenotify", method = RequestMethod.GET)
    public Result.ReturnData<Boolean> updatenotify(@RequestParam("type")String type, @RequestParam("lineId") String lineId);

    @ApiOperation(value = "删除线路接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineId", value = "线路Id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/lineinfos/{lineId}", method = RequestMethod.DELETE)
    public Result.ReturnData<Boolean>  deleteLineinfos(@RequestParam ("lineId")String lineId);

}
