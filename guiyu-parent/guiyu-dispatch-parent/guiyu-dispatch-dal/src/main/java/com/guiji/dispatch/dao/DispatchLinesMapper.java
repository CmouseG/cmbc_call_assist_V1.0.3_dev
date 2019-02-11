package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.DispatchLines;
import com.guiji.dispatch.dao.entity.DispatchLinesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DispatchLinesMapper {
    int countByExample(DispatchLinesExample example);

    int deleteByExample(DispatchLinesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchLines record);

    int insertSelective(DispatchLines record);

    List<DispatchLines> selectByExample(DispatchLinesExample example);

    DispatchLines selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchLines record, @Param("example") DispatchLinesExample example);

    int updateByExample(@Param("record") DispatchLines record, @Param("example") DispatchLinesExample example);

    int updateByPrimaryKeySelective(DispatchLines record);

    int updateByPrimaryKey(DispatchLines record);
}