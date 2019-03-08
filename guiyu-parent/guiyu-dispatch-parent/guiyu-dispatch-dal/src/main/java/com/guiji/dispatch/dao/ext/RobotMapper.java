package com.guiji.dispatch.dao.ext;

import com.guiji.dispatch.entity.DispatchRobotOp;
import com.guiji.dispatch.sys.ResultPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RobotMapper {

    List<DispatchRobotOp> queryDisRobotOpList(@Param("dispatchRobotOp") DispatchRobotOp dispatchRobotOp,
                                              @Param("page")ResultPage<DispatchRobotOp> page);

    DispatchRobotOp queryDisRobotOp(@Param("userId") String userId,
                                    @Param("botstenceId") String botstenceId);

    int addDisRobotOp(DispatchRobotOp opRobot);

    int updDisRobotOp(DispatchRobotOp opRobot);
}