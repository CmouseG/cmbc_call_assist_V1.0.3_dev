package com.guiji.fsagent.api;

import com.guiji.component.result.Result;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.entity.WavLengthVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("guiyu-callcenter-fsagent")
public interface ITemplate {

    @ApiOperation(value = "模板是否存在接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/istempexist/{tempId}")
     Result.ReturnData<Boolean> istempexist(@PathVariable (value="tempId")String tempId);


    @ApiOperation(value = "下载模板录音接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/downloadbotwav/{tempId}")
     Result.ReturnData<Boolean> downloadbotwav(@PathVariable(value="tempId") String tempId);


    @ApiOperation(value = "下载tts话术录音")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "callId", value = "会话Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/downloadttswav")
     Result.ReturnData<Boolean> downloadttswav(@RequestParam("tempId") String tempId, @RequestParam ("callId") String callId);


    @ApiOperation(value = "上传录音")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "模板Id", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/uploadrecord",consumes = "application/json")
     Result.ReturnData<RecordVO> uploadrecord(@RequestBody RecordReqVO recordReqVO);

    @ApiOperation(value = "模板录音文件的时长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/getwavlength/{tempId}")
    Result.ReturnData<List<WavLengthVO>> getwavlength(@PathVariable(value="tempId") String tempId);

}
