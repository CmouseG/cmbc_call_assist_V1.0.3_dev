package com.guiji.da.dao;

import com.guiji.da.dao.entity.RobotCallDetail;
import com.guiji.da.dao.entity.RobotCallDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RobotCallDetailMapper {
    int countByExample(RobotCallDetailExample example);

    int deleteByExample(RobotCallDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RobotCallDetail record);

    int insertSelective(RobotCallDetail record);

    List<RobotCallDetail> selectByExample(RobotCallDetailExample example);

    RobotCallDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RobotCallDetail record, @Param("example") RobotCallDetailExample example);

    int updateByExample(@Param("record") RobotCallDetail record, @Param("example") RobotCallDetailExample example);

    int updateByPrimaryKeySelective(RobotCallDetail record);

    int updateByPrimaryKey(RobotCallDetail record);
}