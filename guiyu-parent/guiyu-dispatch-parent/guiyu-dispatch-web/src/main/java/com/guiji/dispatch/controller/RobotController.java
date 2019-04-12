package com.guiji.dispatch.controller;


import com.guiji.dispatch.dto.DispatchRobotOpDto;
import com.guiji.dispatch.service.RobotService;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.vo.DispatchRobotOpVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dispatch/robot")
public class RobotController {

    @Autowired
    private RobotService robotService;

    //查询计划任务列表机器人
    @ApiOperation(value="查询计划任务列表机器人", notes="查询计划任务列表机器人")
    @RequestMapping(value = "/queryDispatchRobotOp", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultPage<DispatchRobotOpVo> queryDispatchRobotOp(@RequestBody DispatchRobotOpDto dispatchRobotOpDto){
        ResultPage<DispatchRobotOpVo> page = new ResultPage<DispatchRobotOpVo>(dispatchRobotOpDto);
        page = robotService.queryDispatchRobotOp(dispatchRobotOpDto, page);
        return page;
    }

    //修改补充计划任务机器人数量
    @ApiOperation(value="修改补充计划任务机器人数量", notes="修改补充计划任务机器人数量")
    @RequestMapping(value = "/opUserRobotNum", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean opUserRobotNum(@RequestBody DispatchRobotOpDto opRobotDto){
        return robotService.opUserRobotNum(opRobotDto);
    }
}
