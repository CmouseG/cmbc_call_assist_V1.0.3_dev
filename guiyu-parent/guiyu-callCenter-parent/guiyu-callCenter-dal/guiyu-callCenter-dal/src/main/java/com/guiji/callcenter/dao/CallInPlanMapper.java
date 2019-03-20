package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallInPlan;
import com.guiji.callcenter.dao.entity.CallInPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CallInPlanMapper {
    int countByExample(CallInPlanExample example);

    int deleteByExample(CallInPlanExample example);

    int deleteByPrimaryKey(Long callId);

    int insert(CallInPlan record);

    int insertSelective(CallInPlan record);

    List<CallInPlan> selectByExample(CallInPlanExample example);

    CallInPlan selectByPrimaryKey(Long callId);

    int updateByExampleSelective(@Param("record") CallInPlan record, @Param("example") CallInPlanExample example);

    int updateByExample(@Param("record") CallInPlan record, @Param("example") CallInPlanExample example);

    int updateByPrimaryKeySelective(CallInPlan record);

    int updateByPrimaryKey(CallInPlan record);
}