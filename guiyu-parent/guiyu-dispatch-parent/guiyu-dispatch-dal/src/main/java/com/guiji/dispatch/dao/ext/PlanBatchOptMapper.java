package com.guiji.dispatch.dao.ext;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dto.OptPlanDto;
import com.guiji.dispatch.vo.DownLoadPlanVo;
import com.guiji.dispatch.vo.JoinPlanDataVo;
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

    /*批量加入*/
    List<JoinPlanDataVo> getDisPhone(@Param("optPlanDto") OptPlanDto optPlanDto, @Param("limit") Integer limit);

    //  int batchJoinPlan(@Param("plan") DispatchPlan plan, @Param("limit") Integer limit);

    /*批量导出*/
    List<DownLoadPlanVo> queryExportPlanById(@Param("planUuidList") List<Long> planUuidList,
                                             @Param("orgIdList") List<Integer> orgIdList,
                                             @Param("indexStart") Integer indexStart,
                                             @Param("limit") Integer limit);

    int queryExportPlanCountById(@Param("planUuidList") List<Long> planUuidList,
                                 @Param("orgIdList") List<Integer> orgIdList);

    List<DownLoadPlanVo> queryExportPlanList(@Param("optPlanDto") OptPlanDto optPlanDto,
                                             @Param("indexStart") Integer indexStart,
                                             @Param("limit") Integer limit);

    int queryExportPlanCountList(@Param("optPlanDto") OptPlanDto optPlanDto);
}
