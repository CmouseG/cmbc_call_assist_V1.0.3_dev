//package com.guiji.ccmanager.api;
//
//import com.guiji.ccmanager.entity.OutLineInfoAddReq;
//import com.guiji.ccmanager.entity.OutLineInfoUpdateReq;
//import com.guiji.component.result.Result;
//import com.guiji.fsmanager.entity.LineInfoVO;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@FeignClient("guiyu-callcenter-ccmanager")
//public interface ILineOperation {
//
//    @ApiOperation(value = "新增线路")
//    @PostMapping(value = "/out/addLineInfo")
//    Result.ReturnData<Integer> addLineInfo(@RequestBody OutLineInfoAddReq outLineInfoAddReq);
//
//    @ApiOperation(value = "修改线路")
//    @PostMapping(value = "/out/updateLineInfo")
//    Result.ReturnData updateLineInfo(@RequestBody OutLineInfoUpdateReq outLineInfoUpdateReq);
//
//    @ApiOperation(value = "删除线路,呼叫中心的线路id")
//    @GetMapping(value = "/out/deleteLineInfo")
//    Result.ReturnData deleteLineInfo(@RequestParam(value = "LineId") Integer LineId);
//
//    @ApiOperation(value = "批量新增线路")
//    @PostMapping(value = "/out/batchAddLineInfo")
//    Result.ReturnData<List<LineInfoVO>> batchAddLineInfo(@RequestBody List<OutLineInfoAddReq> outLineInfoAddReqList);
//
//}
