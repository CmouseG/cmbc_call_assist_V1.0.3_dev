package com.guiji.da.dao;

import com.guiji.da.dao.entity.RobotCallHis;
import com.guiji.da.dao.entity.RobotCallHisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RobotCallHisMapper {
    int countByExample(RobotCallHisExample example);

    int deleteByExample(RobotCallHisExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RobotCallHis record);

    int insertSelective(RobotCallHis record);

    List<RobotCallHis> selectByExample(RobotCallHisExample example);

    RobotCallHis selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RobotCallHis record, @Param("example") RobotCallHisExample example);

    int updateByExample(@Param("record") RobotCallHis record, @Param("example") RobotCallHisExample example);

    int updateByPrimaryKeySelective(RobotCallHis record);

    int updateByPrimaryKey(RobotCallHis record);
}