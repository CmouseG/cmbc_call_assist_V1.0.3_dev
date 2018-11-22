package com.guiji.robot.dao;

import com.guiji.robot.dao.entity.AiCycleHis;
import com.guiji.robot.dao.entity.AiCycleHisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AiCycleHisMapper {
    int countByExample(AiCycleHisExample example);

    int deleteByExample(AiCycleHisExample example);

    int deleteByPrimaryKey(String id);

    int insert(AiCycleHis record);

    int insertSelective(AiCycleHis record);

    List<AiCycleHis> selectByExample(AiCycleHisExample example);

    AiCycleHis selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AiCycleHis record, @Param("example") AiCycleHisExample example);

    int updateByExample(@Param("record") AiCycleHis record, @Param("example") AiCycleHisExample example);

    int updateByPrimaryKeySelective(AiCycleHis record);

    int updateByPrimaryKey(AiCycleHis record);
}