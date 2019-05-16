package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entityext.MyCallOutPlanQueryEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * author:liyang
 * Date:2019/4/1 15:15
 * Description:
 */
public interface MyCallOutPlanMapper {

    int countCallOutPlan(MyCallOutPlanQueryEntity myCallOutPlanQueryEntity);
    List<CallOutPlan> selectCallOutPlanList(MyCallOutPlanQueryEntity myCallOutPlanQueryEntity);
    List<BigInteger> selectCallIdList(MyCallOutPlanQueryEntity myCallOutPlanQueryEntity);

    void createCallOutPlan(@Param("orgId") Integer orgId);
    void createCallOutDetail(@Param("orgId") Integer orgId);

    List<Map> selectCallPlanRecord4Encrypt(MyCallOutPlanQueryEntity myCallOutPlanQueryEntity);

    void batchDeleteCallRecord(MyCallOutPlanQueryEntity myCallOutPlanQueryEntity);
}
