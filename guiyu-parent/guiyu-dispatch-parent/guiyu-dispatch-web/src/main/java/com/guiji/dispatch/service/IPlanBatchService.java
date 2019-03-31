package com.guiji.dispatch.service;

import com.guiji.dispatch.dto.OptPlanDto;

public interface IPlanBatchService {

    boolean delPlanBatch(OptPlanDto optPlanDto);


    boolean suspendPlanBatch(OptPlanDto optPlanDto);

    //
    boolean stopPlanBatch(OptPlanDto optPlanDto);
}
