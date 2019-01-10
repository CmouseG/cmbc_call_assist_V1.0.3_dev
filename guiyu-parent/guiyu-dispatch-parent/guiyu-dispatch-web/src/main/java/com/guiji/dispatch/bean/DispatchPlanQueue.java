package com.guiji.dispatch.bean;

import com.guiji.dispatch.dao.entity.DispatchPlan;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ty on 2018/11/21.
 */
public class DispatchPlanQueue {

    private static final DispatchPlanQueue instance = new DispatchPlanQueue();

    private BlockingQueue<List<DispatchPlan>> queue = null;

    private DispatchPlanQueue()
    {
        queue = new LinkedBlockingQueue<List<DispatchPlan>>();
    }

    public static DispatchPlanQueue getInstance()
    {
        return DispatchPlanQueue.instance;
    }

    public void produce(List<DispatchPlan> dispatchPlans) throws InterruptedException {

        queue.put(dispatchPlans);
    }

    public List<DispatchPlan> get() throws InterruptedException {
        return queue.take();
    }


}
