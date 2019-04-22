package com.guiji.fsagent.api;

import com.guiji.component.result.Result;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.entity.TtsWav;
import com.guiji.fsagent.entity.WavLengthVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("guiyu-callcenter-fsagent")
public interface ITemplate {

    @ApiOperation(value = "模板是否存在接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/istempexist/{tempId}")
     Result.ReturnData<Boolean> istempexist(@PathVariable (value="tempId")String tempId);

    @ApiOperation(value = "下载tts话术录音")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "callId", value = "会话Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/downloadttswav")
    Result.ReturnData<List<TtsWav>> downloadttswav(@RequestParam("tempId") String tempId, @RequestParam("planUuid") String planUuid, @RequestParam("callId") String callId);


    @ApiOperation(value = "上传录音")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysCode", value = "文件上传系统码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "busiId", value = "上传的影像文件关联的业务ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "busiType", value = "上传的影像文件业务类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fileName", value = "文件名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "recordId", value = "录音文件对应的通话ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recordType", value = "录音类型", dataType = "String", paramType = "query"),
    })
    @PostMapping(value = "/uploadrecord",consumes = "application/json")
    // Result.ReturnData<RecordVO> uploadrecord(@RequestBody RecordReqVO recordReqVO);
     Result.ReturnData<Boolean> uploadrecord(@RequestBody RecordReqVO recordReqVO);

    @ApiOperation(value = "模板录音文件的时长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/getwavlength/{tempId}")
    Result.ReturnData<List<WavLengthVO>> getwavlength(@PathVariable(value="tempId") String tempId);

}
