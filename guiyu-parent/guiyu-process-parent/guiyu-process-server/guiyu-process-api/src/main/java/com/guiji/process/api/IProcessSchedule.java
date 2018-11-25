package com.guiji.process.api;

import com.guiji.component.result.Result;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.process.model.ProcessReleaseVO;
import com.guiji.process.model.UpgrateResouceReq;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 进程管理调度接口
 *
 * @version V1.0
 * @Description: 进程管理接口
 * @author: zhujiayu
 */
@FeignClient("guiyu-process")
public interface IProcessSchedule {

    /**
     * 获取可用的TTS
     * @param model 模型名称
     * @param requestCount 请求数量
     * @return
     */
    @ApiOperation(value = "返回TTS")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "model", value = "模型名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "requestCount", value = "请求数量", dataType = "int", paramType = "query")
    })
    @GetMapping(value="/getTTS")
    Result.ReturnData<List<ProcessInstanceVO>> getTTS(@RequestParam("model") String model, @RequestParam("requestCount") int requestCount);

    /**
     * 获取所有模型
     * @return
     */
    @ApiOperation(value = "返回getAllTTS")
    @GetMapping(value="/getAllTTS")
    Result.ReturnData<List<ProcessInstanceVO>> getAllTTS();

    /**
     * 模型切换
     * @param fromModel
     * @param toModel
     * @param ip
     * @param port
     * @return
     */
    @ApiOperation(value = "changeTTS")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fromModel", value = "原模型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "toModel", value = "新模型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ip", value = "ip", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "port", value = "端口", dataType = "int", paramType = "query")
    })
    @GetMapping(value="/changeTTS")
    Result.ReturnData<Boolean> changeTTS(@RequestParam("fromModel") String fromModel, @RequestParam("toModel") String toModel,@RequestParam("ip") String ip,@RequestParam("port") int port);


    /**
     * 获取可用的Sellbot
     * @param requestCount 请求数量
     * @return
     */
    @ApiOperation(value = "返回Sellbot")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestCount", value = "请求数量", dataType = "int", paramType = "query"),
    })
    @GetMapping(value="/getSellbot")
    Result.ReturnData<List<ProcessInstanceVO>> getSellbot(@RequestParam("requestCount") int requestCount);


    /**
     * 释放资源
     * @param processReleaseVO 释放资源列表
     * @return
     */
    @ApiOperation(value = "释放资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceVOS", value = "释放资源列表", dataType = "ProcessReleaseVO", paramType = "query"),
    })
    @PostMapping(value="/release")
    Result.ReturnData<Boolean> release(@RequestBody ProcessReleaseVO processReleaseVO);


    /**
     * 发布资源
     * @param req 发布资源
     * @return
     */
    @ApiOperation(value = "发布资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "req", value = "发布资源", dataType = "UpgrateResouceReq", paramType = "query"),
    })
    @PostMapping(value="/publishResource")
    Result.ReturnData<Boolean> publishResource(@RequestBody UpgrateResouceReq req);

}

