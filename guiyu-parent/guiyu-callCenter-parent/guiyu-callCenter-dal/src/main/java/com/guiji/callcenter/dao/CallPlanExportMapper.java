package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entityext.CallOutPlanRegistration;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface CallPlanExportMapper {

    List<CallOutPlanRegistration> selectExportCallPlanDetail(@Param("callIds") List<BigInteger> callIds,
                                                             @Param("isDesensitization") Integer isDesensitization,
                                                             @Param("orgId") Integer orgId,
                                                             @Param("orgIdList") List<Integer> orgIdList);

}