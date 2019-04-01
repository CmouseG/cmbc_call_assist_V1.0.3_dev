package com.guiji.dispatch.dao.ext;

import com.guiji.dispatch.dto.OptPlanDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlanBatchOptMapper {

    /*删除*/
    int delPlanBatchById(@Param("planUuidList") List<Long> planUuidList,
                         @Param("orgIdList") List<Integer> orgIdList,
                         @Param("delTime") Date delTime);

    int delPlanBatchByParam(@Param("optPlanDto") OptPlanDto optPlanDto, @Param("delTime") Date delTime);

    /*暂停*/
    int suspendPlanBatchById(@Param("planUuidList") List<Long> planUuidList,
                             @Param("orgIdList") List<Integer> orgIdList,
                             @Param("updTime") Date updTime);

    int suspendPlanBatchByParam(@Param("optPlanDto") OptPlanDto optPlanDto, @Param("updTime") Date updTime);

    /*停止*/
    int stopPlanBatchById(@Param("planUuidList") List<Long> planUuidList,
                          @Param("orgIdList") List<Integer> orgIdList,
                          @Param("updTime") Date updTime);

    int stopPlanBatchByParam(@Param("optPlanDto") OptPlanDto optPlanDto, @Param("updTime") Date updTime);

    /*恢复*/
    int recoveryPlanBatchById(@Param("planUuidList") List<Long> planUuidList,
                          @Param("orgIdList") List<Integer> orgIdList,
                          @Param("updTime") Date updTime);

    int recoveryPlanBatchByParam(@Param("optPlanDto") OptPlanDto optPlanDto, @Param("updTime") Date updTime);
}
