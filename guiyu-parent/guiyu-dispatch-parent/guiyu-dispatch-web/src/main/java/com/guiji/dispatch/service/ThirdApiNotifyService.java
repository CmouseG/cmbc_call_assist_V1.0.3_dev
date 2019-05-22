package com.guiji.dispatch.service;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;

/**
 * @Classname ThirdApiNotifyService
 * @Description TODO
 * @Date 2019/5/23 11:26
 * @Created by qinghua
 */
public interface ThirdApiNotifyService {

    void singleNotify(DispatchPlan plan);

    void batchNotify(DispatchPlan plan);

}
