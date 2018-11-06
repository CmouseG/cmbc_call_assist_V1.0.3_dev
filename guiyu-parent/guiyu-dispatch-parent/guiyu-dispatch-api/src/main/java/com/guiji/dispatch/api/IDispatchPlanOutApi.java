package com.guiji.dispatch.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.dispatch.model.DispatchPlan;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.guiji.component.result.Result;
/**
 * 调度中心任务调度接口
 *
 * @version V1.0
 * @Description: 调度中心任务调度接口
 * @author: xujin
 */
@FeignClient("guiyu-dispatch")
public interface IDispatchPlanOutApi {

    /**
     * 返回可以拨打的任务给呼叫中心
     *
     * @param schedule 请求参数
     * @return 响应报文
     * @throws Exception 异常
     */
    @ApiOperation(value = "返回可以拨打的任务给呼叫中心")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "requestCount", value = "请求数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "lineId", value = "线路id", dataType = "int", paramType = "query")
    })
    @GetMapping(value="out/queryAvailableSchedules")
   public Result.ReturnData<List<DispatchPlan>> queryAvailableSchedules(@RequestParam("userId") Integer userId,@RequestParam("requestCount") int requestCount,@RequestParam("lineId") int lineId);


    /**
     * 完成
     *
     * @param planUuid 任务id
     * @return 接受号码呼叫完成通知
     * @throws Exception 异常
     */
    @ApiOperation(value = "接受号码呼叫完成通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planUuid", value = "planUuid", dataType = "String", paramType = "query"),
    })
    @GetMapping(value="out/successSchedule")
    boolean successSchedule(@RequestParam("planUuid") String planUuid);

}

