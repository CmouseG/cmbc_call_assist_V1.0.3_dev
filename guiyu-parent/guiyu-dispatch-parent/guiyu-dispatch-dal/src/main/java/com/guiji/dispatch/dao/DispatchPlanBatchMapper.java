package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dao.entity.DispatchPlanBatchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DispatchPlanBatchMapper {
    int countByExample(DispatchPlanBatchExample example);

    int deleteByExample(DispatchPlanBatchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchPlanBatch record);

    int insertSelective(DispatchPlanBatch record);

    List<DispatchPlanBatch> selectByExample(DispatchPlanBatchExample example);

    DispatchPlanBatch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchPlanBatch record, @Param("example") DispatchPlanBatchExample example);

    int updateByExample(@Param("record") DispatchPlanBatch record, @Param("example") DispatchPlanBatchExample example);

    int updateByPrimaryKeySelective(DispatchPlanBatch record);

    int updateByPrimaryKey(DispatchPlanBatch record);
}