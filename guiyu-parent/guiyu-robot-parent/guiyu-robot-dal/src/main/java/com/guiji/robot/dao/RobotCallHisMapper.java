package com.guiji.robot.dao;

import com.guiji.robot.dao.entity.RobotCallHis;
import com.guiji.robot.dao.entity.RobotCallHisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RobotCallHisMapper {
    int countByExample(RobotCallHisExample example);

    int deleteByExample(RobotCallHisExample example);

    int deleteByPrimaryKey(String id);

    int insert(RobotCallHis record);

    int insertSelective(RobotCallHis record);

    List<RobotCallHis> selectByExampleWithBLOBs(RobotCallHisExample example);

    List<RobotCallHis> selectByExample(RobotCallHisExample example);

    RobotCallHis selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RobotCallHis record, @Param("example") RobotCallHisExample example);

    int updateByExampleWithBLOBs(@Param("record") RobotCallHis record, @Param("example") RobotCallHisExample example);

    int updateByExample(@Param("record") RobotCallHis record, @Param("example") RobotCallHisExample example);

    int updateByPrimaryKeySelective(RobotCallHis record);

    int updateByPrimaryKeyWithBLOBs(RobotCallHis record);

    int updateByPrimaryKey(RobotCallHis record);
}