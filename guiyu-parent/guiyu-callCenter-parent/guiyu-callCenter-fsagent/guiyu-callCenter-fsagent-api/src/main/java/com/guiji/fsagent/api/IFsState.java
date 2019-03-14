package com.guiji.fsagent.api;

import com.guiji.component.result.Result;
import com.guiji.fsagent.entity.FsInfoVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@FeignClient("guiyu-callcenter-fsagent")
public interface IFsState {
    @ApiOperation(value = "检查服务健康状态")
    @RequestMapping(value = "/ishealthy", method = RequestMethod.GET)
     Result.ReturnData<String> ishealthy();

    @ApiOperation(value = "获取freeswitch基本信息")
    @RequestMapping(value = "/fsinfo", method = RequestMethod.GET)
     Result.ReturnData<FsInfoVO> fsinfo();
}
