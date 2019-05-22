package com.guiji.dispatch.service;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.enums.PlanStatusEnum;
import com.guiji.dispatch.model.IPlanThirdBatchDialVo;
import com.guiji.dispatch.model.IPlanThirdBatchPhoneVo;
import com.guiji.dispatch.model.QueryPlanThirdRo;

import java.util.List;

/**
 * @Classname IDispatchPlanBatchService
 * @Description TODO
 * @Date 2019/5/21 18:24
 * @Created by qinghua
 */
public interface IDispatchPlanBatchService {

    /**
     * 初始化批次
     * @param batch
     * @return
     */
    DispatchPlanBatch addDispatchPlanBatch(DispatchPlanBatch batch);

    /**
     * 根据批次名和userId查询批次信息
     * @param batchName
     * @param userId
     * @return
     */
    DispatchPlanBatch queryPlanBatchByName(String batchName, Integer userId);

    /**
     * 操作任务状态
     * 暂停、停止、恢复
     * @param batchId
     * @param orgIds
     * @param status
     */
    void updatePlanBatchStatus(Integer batchId, List<Integer> orgIds, PlanStatusEnum status);

    /**
     * 查询拨打情况
     * @param batchName
     * @param userId
     * @return
     */
    IPlanThirdBatchDialVo getPlanThirdBatchDial(String batchName, Integer userId);

    /**
     * 查询号码列表
     * @param ro
     * @return
     */
    IPlanThirdBatchPhoneVo queryPlanThirdBatchPage(QueryPlanThirdRo ro);
}
