package com.guiji.dispatch.dao.ext;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PlanTableMapper {

    //创建dispatch_plan表
    int createPlanTable(@Param("orgId") Integer orgId);

    //创建dispatch_table表索引
    int createPlanTableIndex(@Param("orgId") Integer orgId,
                             @Param("column") String column);

    //创建dispatch_line表
    int createLineTable(@Param("orgId") Integer orgId);

    //创建dispatch_line表
    int createLineTableIndex(@Param("orgId") Integer orgId,
                             @Param("column") String column);
}