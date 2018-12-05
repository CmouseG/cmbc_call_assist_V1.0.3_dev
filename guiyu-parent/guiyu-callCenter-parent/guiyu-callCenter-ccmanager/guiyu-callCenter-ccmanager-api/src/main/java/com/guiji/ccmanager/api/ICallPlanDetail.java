package com.guiji.ccmanager.api;

import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.component.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/3 0003 14:23
 * @Description:
 */
@FeignClient("guiyu-callcenter-ccmanager")
public interface ICallPlanDetail {

   @ApiOperation(value = "查看通话记录详情,供后台服务使用")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "callId", value = "callId", dataType = "String", paramType = "query", required = true)
   })
   @GetMapping(value="getCallPlanDetailRecord")
   Result.ReturnData<List<CallPlanDetailRecordVO>> getCallPlanDetailRecord(List<String> callIds);

}
