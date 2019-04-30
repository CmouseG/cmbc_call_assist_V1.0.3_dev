package com.guiji.ccmanager.api;

import com.guiji.ccmanager.entity.CallPlanUuidQuery;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.ccmanager.vo.CallRecordReq;
import com.guiji.component.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/3 0003 14:23
 * @Description:
 */
@FeignClient("guiyu-callcenter-ccmanager")
public interface ICallPlanDetail {

    @ApiOperation(value = "查看通话记录详情")
    @PostMapping(value="getCallPlanDetailRecord")
    public Result.ReturnData<List<CallPlanDetailRecordVO>> getCallPlanDetailRecord(@RequestBody CallPlanUuidQuery callPlanUuidQuery);


    @ApiOperation(value = "查看通话记录详情，前台页面使用,后台可使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callId", value = "callId", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping(value = "getCallDetailApi")
    Result.ReturnData<CallPlanDetailRecordVO> getCallDetailApi(@RequestParam(value="callId") String callId,@RequestParam(value="orgId") Integer orgId);

    @ApiOperation(value = "获取通话记录列表")
    @PostMapping(value = "getCallRecordList")
    Result.ReturnData<Map> getCallRecordList(CallRecordReq callRecordReq);

    @ApiOperation(value = "通过电话号码，获取通话记录列表")
    @GetMapping(value = "getCallRecordListByPhone")
    Result.ReturnData<List> getCallRecordListByPhone(@RequestParam(value="phone")  String phone,
                                                     @RequestParam(value="authLevel")  Integer authLevel,
                                                     @RequestParam(value="orgId")  Integer orgId);
}
