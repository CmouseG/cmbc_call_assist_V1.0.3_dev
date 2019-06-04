package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dao.entity.DispatchPlanBatchExample;

import java.util.Date;
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

    //根据时间查询批次
    List<DispatchPlanBatch> queryBatchByTime(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    //根据时间查询批次用户ID
    List<Integer> queryBatchUserIdByTime(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    //根据批次号更新批次通知状态
    int updNotifyStatusByBatch(@Param("batchId") Integer batchId, @Param("statusNotify") Integer statusNotify);
}