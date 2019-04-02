package com.guiji.dispatch.service;

import com.guiji.dispatch.dto.JoinPlanDto;
import com.guiji.dispatch.dto.OptPlanDto;

public interface IPlanBatchService {

    //批量删除
    boolean delPlanBatch(OptPlanDto optPlanDto);

    //批量暂停
    boolean suspendPlanBatch(OptPlanDto optPlanDto);

    //批量停止
    boolean stopPlanBatch(OptPlanDto optPlanDto);

    //批量恢复
    boolean recoveryPlanBatch(OptPlanDto optPlanDto);

    //批量加入
    boolean joinPlanBatch(JoinPlanDto joinPlanDto);

    //批量导出
    boolean exportPlanBatch(OptPlanDto optPlanDto);
}
