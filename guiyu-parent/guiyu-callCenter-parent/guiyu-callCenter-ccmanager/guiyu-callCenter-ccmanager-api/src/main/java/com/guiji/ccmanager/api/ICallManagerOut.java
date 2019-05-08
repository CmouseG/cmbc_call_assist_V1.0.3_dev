//package com.guiji.ccmanager.api;
//
//import com.guiji.ccmanager.entity.LineConcurrent;
//import com.guiji.component.result.Result;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
///**
// * @Auther: 黎阳
// * @Date: 2018/10/30 0030 09:25
// * @Description:
// */
//@FeignClient("guiyu-callcenter-ccmanager")
//public interface ICallManagerOut {
//
//
////    @ApiOperation(value = "根据线路id查询线路名称")
////    @GetMapping(value="out/getLineInfoById")
////    Result.ReturnData<String>  getLineInfoById(@RequestParam("lineId") Integer lineId);
//
//
///*
//    @ApiOperation(value = "获取客户线路列表，供调度中心使用")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "customerId", value = "客户id", dataType = "String", paramType = "query", required = true)
//    })
//    @GetMapping(value="out/getLineInfos")
//    Result.ReturnData<List<LineConcurrent>> getLineInfos(@RequestParam("customerId") String customerId);
//*/
//
//
///*
//    @ApiOperation(value = "获取客户线路列表，供汇总使用")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "customerId", value = "客户id", dataType = "String", paramType = "query", required = true),
//            @ApiImplicitParam(name = "isSuperAdmin", value = "是否是管理员", dataType = "Boolean", paramType = "query", required = true)
//    })
//    @GetMapping(value="out/getLineNameAndCount")
//    public Result.ReturnData<List<LineConcurrent>> getLineNameAndCount(@RequestParam(value = "customerId", required = true) String customerId,
//                                                                       @RequestParam(value = "isSuperAdmin", required = true) Boolean isSuperAdmin);
//*/
//
///*    @ApiOperation(value = "获取callId获取通话记录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "callId", value = "call_out_plan的id", dataType = "String", paramType = "query", required = true)
//    })
//    @GetMapping(value="getCallRecordById")
//    Result.ReturnData<CallOutPlan> getCallRecordById(@RequestParam(value = "callId", required = true) String callId);*/
//}
