package com.guiji.fsagent.api;

import com.guiji.component.result.Result;

import com.guiji.fsagent.entity.FsSipOprVO;
import com.guiji.fsagent.entity.SimCardOprVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("guiyu-callcenter-fsagent")
public interface ISimCardOperate {

    @ApiOperation(value = "创建网关")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startCount", value = "起始账号", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "startPwd", value = "起始密码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "countsStep", value = "sip账号步长", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pwdStep", value = "sip密码步长", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "countNum", value = "账号数量", dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/simgatewayopr",consumes = "application/json")
    Result.ReturnData<FsSipOprVO> createGateway(@RequestBody SimCardOprVO simCardOprVO);

    @ApiOperation(value = "删除网关")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatewayId", value = "网关id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/simgatewayopr", method = RequestMethod.DELETE)
     Result.ReturnData<Boolean>  deleteGateway(@RequestParam(value = "startCount") int startCount, @RequestParam(value = "countsStep") int countsStep, @RequestParam(value = "countNum") int countNum);
}
