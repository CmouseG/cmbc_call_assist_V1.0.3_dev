package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CallOutPlanMapper {
    int countByExample(CallOutPlanExample example);

    int deleteByExample(CallOutPlanExample example);

    int deleteByPrimaryKey(BigInteger callId);

    int insert(CallOutPlan record);

    int insertSelective(CallOutPlan record);

    List<CallOutPlan> selectByExample(CallOutPlanExample example);
    List<CallOutPlan> selectByExample4Encrypt(CallOutPlanExample example);
    List<Map> selectCallPlanRecord4Encrypt(Map map);
    int countCallRecordList(Map map);

    CallOutPlan selectByPrimaryKey(BigInteger callId);

    int updateByExampleSelective(@Param("record") CallOutPlan record, @Param("example") CallOutPlanExample example);
    int updateCallStateIntentReason(@Param("record") CallOutPlan record, @Param("example") CallOutPlanExample example);

    int updateByExample(@Param("record") CallOutPlan record, @Param("example") CallOutPlanExample example);

    int updateByPrimaryKeySelective(CallOutPlan record);

    int updateByPrimaryKey(CallOutPlan record);

    void updateNotOverWriteIntent(CallOutPlan callPlan);
}