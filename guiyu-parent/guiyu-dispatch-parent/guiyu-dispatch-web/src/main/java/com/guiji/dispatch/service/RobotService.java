package com.guiji.dispatch.service;

import com.guiji.dispatch.dto.DispatchRobotOpDto;
import com.guiji.dispatch.dto.QueryDisRobotOpDto;
import com.guiji.dispatch.entity.DispatchRobotOp;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.vo.DispatchRobotOpVo;

import java.util.List;

public interface RobotService {

    //查询计划任务列表机器人
    ResultPage<DispatchRobotOpVo> queryDispatchRobotOp(DispatchRobotOpDto dispatchRobotOpDto, ResultPage<DispatchRobotOpVo> page);


    boolean opUserRobotNum(DispatchRobotOpDto opRobotDto);

    //查询补充分配机器人操作列表
    List<DispatchRobotOp> queryDisRobotOpList(QueryDisRobotOpDto queryDisRobotOpDto, ResultPage<DispatchRobotOp> page);
}
