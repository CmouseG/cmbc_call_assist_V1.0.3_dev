package com.guiji.dispatch.service;

import com.guiji.dispatch.dao.entity.DispatchPlan;

import java.util.List;

/**
 * Created by ty on 2019/1/7.
 */
public interface IPhonePlanQueueService {
    void execute() throws Exception;

    List<DispatchPlan> getDispatchPlan(int hour);

    boolean pushPlan2Queue(List<DispatchPlan> dispatchPlanList);
}
