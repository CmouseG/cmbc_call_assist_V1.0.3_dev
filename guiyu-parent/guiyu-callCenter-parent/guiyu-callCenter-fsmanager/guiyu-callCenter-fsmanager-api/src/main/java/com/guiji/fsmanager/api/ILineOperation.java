package com.guiji.fsmanager.api;


import com.guiji.component.result.Result;
import com.guiji.fsmanager.entity.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("guiyu-callcenter-fsmanager")
public interface ILineOperation {

    @ApiOperation(value = "新增线路")
    @PostMapping(value = "/out/addLineInfo")
    Result.ReturnData<Integer> addLineInfo(@RequestBody OutLineInfoAddReq outLineInfoAddReq);

    @ApiOperation(value = "修改线路")
    @PostMapping(value = "/out/updateLineInfo")
    Result.ReturnData updateLineInfo(@RequestBody OutLineInfoUpdateReq outLineInfoUpdateReq);

    @ApiOperation(value = "删除线路,呼叫中心的线路id")
    @GetMapping(value = "/out/deleteLineInfo")
    Result.ReturnData deleteLineInfo(@RequestParam(value = "LineId") Integer LineId);

    @ApiOperation(value = "批量新增线路")
    @PostMapping(value = "/out/batchAddLineInfo")
    Result.ReturnData<List<LineInfoVO>> batchAddLineInfo(@RequestBody List<OutLineInfoAddReq> outLineInfoAddReqList);

    @ApiOperation(value = "批量新增单个sim卡网关端口的线路")
    @PostMapping(value = "/out/batchAddSimLineInfo")
    Result.ReturnData<List<LineInfoVO>> batchAddSimLineInfo(@RequestParam(value = "lineIp") String lineIp,@RequestParam(value = "linePort") String linePort,@RequestBody List<OutLineInfoAddReq> outLineInfoAddReqList);

    @ApiOperation(value = "批量删除线路,呼叫中心的线路id")
    @GetMapping(value = "/out/batchDeleteLineInfo")
    Result.ReturnData batchDeleteLineInfo(@RequestBody List<Integer> lineIds);

    @ApiOperation(value = "根据线路id获取freeswitch的信息")
    @GetMapping(value = "/out/getFsInfoByLineId")
    Result.ReturnData<FsLineInfoVO> getFsInfoByLineId(@RequestParam(value = "lineId") Integer lineId);

    @ApiOperation(value = "根据fsagent服务的serviceId获取线路配置文件")
    @GetMapping(value = "/out/linexmlinfosByFsagentId")
    Result.ReturnData<List<LineXmlnfoVO>> linexmlinfosByFsagentId(@RequestParam("serviceId") String serviceId);

    @ApiOperation(value = "根据线路id获取线路配置文件")
    @GetMapping(value = "/out/getLinexmlinfoByLineId")
    Result.ReturnData<List<LineXmlnfoVO>> getLinexmlinfoByLineId(@RequestParam(value = "lineId") Integer lineId);
}
