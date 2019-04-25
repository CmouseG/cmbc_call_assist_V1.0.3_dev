package com.guiji.dispatch.service;

import com.guiji.dispatch.dao.entity.DispatchPlan;

import java.util.List;

/**
 * Created by ty on 2019/1/7.
 */
public interface IPhonePlanQueueService {
    void execute() throws Exception;

    boolean pushPlan2Queue(List<DispatchPlan> dispatchPlanList,String queue);

    boolean cleanQueue();

    boolean cleanQueueByUserId(String userId);

    boolean cleanQueueByQueueName(String queueName);
}
