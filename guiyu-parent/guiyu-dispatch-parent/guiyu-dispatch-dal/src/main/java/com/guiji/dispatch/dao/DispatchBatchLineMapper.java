package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.dao.entity.DispatchBatchLineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DispatchBatchLineMapper 
{
    long countByExample(DispatchBatchLineExample example);

    int deleteByExample(DispatchBatchLineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchBatchLine record);

    int insertSelective(DispatchBatchLine record);

    List<DispatchBatchLine> selectByExample(DispatchBatchLineExample example);

    DispatchBatchLine selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchBatchLine record, @Param("example") DispatchBatchLineExample example);

    int updateByExample(@Param("record") DispatchBatchLine record, @Param("example") DispatchBatchLineExample example);

    int updateByPrimaryKeySelective(DispatchBatchLine record);

    int updateByPrimaryKey(DispatchBatchLine record);
}