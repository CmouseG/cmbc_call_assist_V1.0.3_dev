package com.guiji.dispatch.controller;

import com.guiji.dispatch.api.IJssPlanService;
import com.guiji.dispatch.model.CommonResponse;
import com.guiji.dispatch.model.Schedule;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JssPlanController {

    private IJssPlanService jssPlanService;

    /**
     * 向调度中心提交任务
     *
     * @param schedule 任务
     * @return 响应报文
     * @throws Exception 异常
     */
    @ApiOperation(value = "提交任务", notes = "向调度中心提交任务")
    @RequestMapping(value = "/addSchedule", method = RequestMethod.POST)
    CommonResponse addSchedule(@RequestBody final Schedule schedule) throws Exception {
        return jssPlanService.addSchedule(schedule);
    }

    /**
     * 查询任务列表
     *
     * @param userId 用户id
     * @return 响应报文
     * @throws Exception 异常
     */
    @ApiOperation(value = "查询任务列表", notes = "查询任务列表")
    @RequestMapping(value = "/querySchedules", method = RequestMethod.GET)
    CommonResponse querySchedules(@RequestParam final String userId) throws Exception {
        return jssPlanService.querySchedules(userId);
    }

    /**
     * 暂停任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    @ApiOperation(value = "暂停任务", notes = "暂停任务")
    @RequestMapping(value = "/pauseSchedule", method = RequestMethod.POST)
    CommonResponse pauseSchedule(@RequestBody final String planUuid) throws Exception {
        return jssPlanService.pauseSchedule(planUuid);
    }

    /**
     * 恢复任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    @ApiOperation(value = "恢复任务", notes = "恢复任务")
    @RequestMapping(value = "/resumeSchedule", method = RequestMethod.POST)
    CommonResponse resumeSchedule(@RequestBody final String planUuid) throws Exception {
        return jssPlanService.resumeSchedule(planUuid);
    }

    /**
     * 停止任务
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    @ApiOperation(value = "停止任务", notes = "停止任务")
    @RequestMapping(value = "/stopSchedule", method = RequestMethod.POST)
    CommonResponse stopSchedule(@RequestBody final String planUuid) throws Exception {
        return jssPlanService.stopSchedule(planUuid);
    }

    /**
     * 返回可以拨打的任务给呼叫中心
     *
     * @param userId 用户id
     * @return 响应报文
     * @throws Exception 异常
     */
    @ApiOperation(value = "返回可以拨打的任务给呼叫中心", notes = "返回可以拨打的任务给呼叫中心")
    @RequestMapping(value = "/queryAvailableSchedule", method = RequestMethod.GET)
    CommonResponse queryAvailableSchedule(@RequestParam final String userId, @RequestParam final String taskNum) throws Exception {
        return jssPlanService.queryAvailableSchedule(userId, taskNum);
    }

    /**
     * 查询任务提交处理结果
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    @ApiOperation(value = "查询任务提交处理结果", notes = "查询任务提交处理结果")
    @RequestMapping(value = "/queryScheduleResult", method = RequestMethod.GET)
    CommonResponse queryScheduleResult(@RequestParam final String planUuid) throws Exception {
        return jssPlanService.querySchedules(planUuid);
    }

    /**
     * 查询任务提交处理结果
     *
     * @param planUuid 任务id
     * @return 响应报文
     * @throws Exception 异常
     */
    @ApiOperation(value = "获取任务执行情况", notes = "获取任务执行情况")
    @RequestMapping(value = "/queryExecuteResult", method = RequestMethod.GET)
    CommonResponse queryExecuteResult(@RequestParam final String planUuid) throws Exception {
        return jssPlanService.queryExecuteResult(planUuid);
    }
}
