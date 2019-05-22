package com.guiji.dispatch.dao.ext;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.sys.ResultPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BatchPlanExtMapper {

    //变更暂停
    int suspendPlanPhoneByBatch(@Param("batchId") Integer batchId, @Param("orgIdList") List<Integer> orgIdList, @Param("updTime") Date updTime);

    //变更停止
    int stopPlanPhoneByBatch(@Param("batchId") Integer batchId, @Param("orgIdList") List<Integer> orgIdList, @Param("updTime") Date updTime);

    //变更恢复
    int recoveryPlanPhoneByBatch(@Param("batchId") Integer batchId, @Param("orgIdList") List<Integer> orgIdList, @Param("updTime") Date updTime);

    int totalPlanCountByStatus(@Param("batchId") Integer batchId, @Param("statusPlan") Integer statusPlan, @Param("orgIdList") List<Integer> orgIdList);

    int totalErrorCountByBatch(@Param("batchId") Integer batchId);

    List<DispatchPlan> queryErrorPhoneByBatch(@Param("batchId") Integer batchId);

    //查询三方批次号码列表拼接
    List<DispatchPlan> queryPlanThirdBatchPage(@Param("batchId") Integer batchId, @Param("orgIdList") List<Integer> orgIdList,
                                               @Param("page") ResultPage<DispatchPlan> page);

    //查询三方批次号码数量
    int queryPlanThirdCount(@Param("batchId") Integer batchId, @Param("orgIdList") List<Integer> orgIdList);
}
