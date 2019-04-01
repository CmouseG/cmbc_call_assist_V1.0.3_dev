package com.guiji.dispatch.api;

import java.util.List;

import com.guiji.dispatch.model.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
/**
 * 调度中心任务调度接口
 *
 * @version V1.0
 * @Description: 调度中心任务调度接口
 * @author: xujin
 */
@FeignClient("GUIYU-DISPATCH-WEB")
public interface IDispatchPlanOut {

    /**
     * 返回可以拨打的任务给呼叫中心
     *
     * @param schedule 请求参数
     * @return 响应报文
     */
    @ApiOperation(value = "返回可以拨打的任务给呼叫中心")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "requestCount", value = "请求数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "lineId", value = "线路id", dataType = "int", paramType = "query")
    })
    @GetMapping(value="out/queryAvailableSchedules")
   public Result.ReturnData<List<DispatchPlan>>  queryAvailableSchedules(@RequestParam("userId") Integer userId,@RequestParam("requestCount") int requestCount,@RequestParam("lineId") int lineId);


    /**
     * 完成
     *
     * @param planUuid 任务id
     * @return 接受号码呼叫完成通知
     */
    @ApiOperation(value = "接受号码呼叫完成通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planUuid", value = "planUuid", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "label", value = "label", dataType = "String", paramType = "query")
    })
    @GetMapping(value="out/successSchedule")
    Result.ReturnData<Boolean> successSchedule(@RequestParam("planUuid") String planUuid,@RequestParam("label") String label);
    
    
    
    /**
     * 完成升级模板
     *
     * @param planUuid 任务id
     * @return 接受号码呼叫完成通知
     */
    @ApiOperation(value = "接受号码呼叫完成通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tempId", value = "tempId", dataType = "String", paramType = "query"),
    })
    @GetMapping(value="out/successSchedule4TempId")
    Result.ReturnData<Boolean> successSchedule4TempId(@RequestParam("tempId") String tempId);
    
    /**
     * 接受当前升级中的机器人id
     *
     * @param planUuid RobotId
     * @return 接受号码呼叫完成通知
     */
    @ApiOperation(value = "接受当前升级中的机器人id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "RobotId", value = "RobotId", dataType = "String", paramType = "query"),
    })
    @GetMapping(value="out/receiveRobotId")
    Result.ReturnData<Boolean> receiveRobotId(@RequestParam("RobotId") String RobotId);

    /**
     * 初始化系统拨打电话资源池
     */
    @ApiOperation(value = "初始化系统拨打电话资源池")
    @GetMapping(value="out/initResourcePool")
    Result.ReturnData<Boolean> initResourcePool();
    
    
    @ApiOperation(value = "微信小程序获取计划数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgCode", value = "orgCode", dataType = "String", paramType = "query"),
    })
    @GetMapping(value="out/getPlanCountByUserId")
    Result.ReturnData<PlanCountVO> getPlanCountByUserId(@RequestParam("orgCode") String orgCode);
    
    
    @ApiOperation(value = "微信小程序一键停止拨打")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgCode", value = "orgCode", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "type", dataType = "String", paramType = "query"),
    })
    @GetMapping(value="out/opertationStopPlanByUserId")
    Result.ReturnData<Boolean> opertationStopPlanByUserId(@RequestParam("orgCode") String orgCode,@RequestParam("type") String type);
    
	@ApiOperation(value = "修改意向标签")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "planuuid", value = "planuuid", dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "label", value = "label", dataType = "String", paramType = "query") })
	@GetMapping(value = "out/updateLabelByUUID")
	ReturnData<Boolean>  updateLabelByUUID(@RequestParam("planuuid") String planuuid,@RequestParam("label") String label);

    
    @ApiOperation(value = "根据用户判断当前线路是否使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineId", value = "lineId", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "userId", dataType = "Integer", paramType = "query"),
    })
    @RequestMapping(value="out/lineIsUsedByUserId", method={RequestMethod.POST, RequestMethod.GET})
    Result.ReturnData<Boolean> lineIsUsedByUserId(@RequestParam("lineId") Integer lineId,@RequestParam("userId") Integer userId);
    
    
    @ApiOperation(value = "判断当前线路是否使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineId", value = "lineId", dataType = "Integer", paramType = "query"),
    })
    @RequestMapping(value="out/lineIsUsed", method={RequestMethod.POST, RequestMethod.GET})
    Result.ReturnData<Boolean> lineIsUsed(@RequestBody LineIsUseDto LineIsUseDto);


    //查询计划任务
    @ApiOperation(value="根据uuId查询计划任务")
    @RequestMapping(value="/dispatch/queryDispatchPlanById", method={RequestMethod.GET})
    Result.ReturnData<DispatchPlan> queryDispatchPlanById(@RequestParam("planUuid") String planUuid);

   @ApiOperation(value="根据uuId查询计划任务备注")
   @RequestMapping(value="/dispatch/queryPlanRemarkById", method={RequestMethod.GET})
   Result.ReturnData<String> queryPlanRemarkById(@RequestParam("planUuid") String planUuid);

   @ApiOperation(value="新增文件导出记录")
   @RequestMapping(value="/dispatch/out/addExportFile", method={RequestMethod.POST})
   Result.ReturnData<ExportFileRecordVo> addExportFile(@RequestBody ExportFileDto exportFileDto);

    /**
     * 变更文件导出记录状态
     * @param recordId  记录唯一ID
     * @param status    状态 0-进行中 1-已完成 2-取消  3-删除 4-失败
     * @return
     */
   @ApiOperation(value="变更文件导出记录状态")
   @RequestMapping(value="/dispatch/out/updExportFile", method={RequestMethod.POST})
   Result.ReturnData<Boolean> updExportFile(@RequestParam("recordId") String recordId, @RequestParam("status") Integer status,
                                            @RequestParam("fileGenerateUrl") String fileGenerateUrl);

}

