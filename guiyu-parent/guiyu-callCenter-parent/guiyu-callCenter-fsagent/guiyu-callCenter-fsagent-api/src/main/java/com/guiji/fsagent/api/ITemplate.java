package com.guiji.fsagent.api;

import com.guiji.component.result.Result;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

public interface ITemplate {

    @ApiOperation(value = "模板是否存在接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/istempexist/{tempId}")
    public Result.ReturnData<Boolean> istempexist(@PathVariable (value="tempId")String tempId);


    @ApiOperation(value = "模板是否存在接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/downloadbotwav/{tempId}")
    public Result.ReturnData<Boolean> downloadbotwav(@PathVariable(value="tempId") String tempId);


    @ApiOperation(value = "下载tts话术录音")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "模板Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "callId", value = "会话Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/downloadttswav")
    public Result.ReturnData<Boolean> downloadttswav(String tempId, String callId);


//    @ApiOperation(value = "上传录音")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "fileName", value = "模板Id", dataType = "String", paramType = "query")
//    })
//    @GetMapping(value="/uploadrecord/{fileName}")

    @RequestMapping(value = "/uploadrecord", method = RequestMethod.POST)
    public Result.ReturnData<RecordVO> uploadrecord(@RequestBody RecordReqVO recordReqVO);
}
