package com.guiji.da.dao;

import com.guiji.da.dao.entity.RobotCallProcessStat;
import com.guiji.da.dao.entity.RobotCallProcessStatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RobotCallProcessStatMapper {
    int countByExample(RobotCallProcessStatExample example);

    int deleteByExample(RobotCallProcessStatExample example);

    int deleteByPrimaryKey(String id);

    int insert(RobotCallProcessStat record);

    int insertSelective(RobotCallProcessStat record);

    List<RobotCallProcessStat> selectByExample(RobotCallProcessStatExample example);

    RobotCallProcessStat selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RobotCallProcessStat record, @Param("example") RobotCallProcessStatExample example);

    int updateByExample(@Param("record") RobotCallProcessStat record, @Param("example") RobotCallProcessStatExample example);

    int updateByPrimaryKeySelective(RobotCallProcessStat record);

    int updateByPrimaryKey(RobotCallProcessStat record);
}