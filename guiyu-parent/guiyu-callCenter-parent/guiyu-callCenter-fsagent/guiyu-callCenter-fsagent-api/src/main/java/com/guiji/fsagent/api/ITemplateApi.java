package com.guiji.fsagent.api;

import com.guiji.common.result.Result;
import com.guiji.fsagent.entity.RecordVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ITemplateApi {

    @ApiOperation(value = "模板是否存在接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/istempexist/{tempId}")
    public Result.ReturnData<Boolean> istempexist(@PathVariable String tempId);


    @ApiOperation(value = "模板是否存在接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/downloadbotwav/{tempId}")
    public Result.ReturnData<Boolean> downloadbotwav(@PathVariable String tempId);


    @ApiOperation(value = "下载tts话术录音")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "callId", value = "会话Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/downloadttswav")
    public Result.ReturnData<Boolean> downloadttswav(String tempId, String callId);


    @ApiOperation(value = "上传录音")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "模板Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/uploadrecord/{fileName}")
    public Result.ReturnData<RecordVO> uploadrecord(@PathVariable String fileName);
}
