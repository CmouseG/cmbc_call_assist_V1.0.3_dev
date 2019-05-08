package com.guiji.fsmanager.api;

import com.guiji.component.result.Result;

import com.guiji.fsmanager.entity.FsSipVO;
import com.guiji.fsmanager.entity.SimCardVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("guiyu-callcenter-fsmanager")
public interface ISimCard {

    @ApiOperation(value = "创建网关")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startCount", value = "起始账号", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "startPwd", value = "起始密码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "countsStep", value = "sip账号步长", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pwdStep", value = "sip密码步长", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "countNum", value = "账号数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "gatewayId", value = "网关id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/simgateway", method = RequestMethod.POST)
    Result.ReturnData<FsSipVO> createGateway(@RequestBody SimCardVO simCardVO);

    @ApiOperation(value = "删除网关")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatewayId", value = "网关id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/simgateway/{gatewayId}", method = RequestMethod.DELETE)
     Result.ReturnData<Boolean>  deleteGateway(@PathVariable(value = "gatewayId") String gatewayId);

    @ApiOperation(value = "根据serviceId获取网关账号配置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "line_sim角色的fsagent_id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/getAccount", method = RequestMethod.GET)
    Result.ReturnData<List<SimCardVO>> getGatewayAccount(@RequestParam(value = "serviceId") String  serviceId);
}
