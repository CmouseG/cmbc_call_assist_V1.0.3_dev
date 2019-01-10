package com.guiji.dispatch.batchimport;

import com.guiji.dispatch.dao.entity.DispatchPlan;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ty on 2018/2/28.
 */
public class BatchImortQueue {

    private static final BatchImortQueue instance = new BatchImortQueue();

    private BlockingQueue<DispatchPlan> queue = null;

    private BatchImortQueue()
    {
        queue = new LinkedBlockingQueue<DispatchPlan>();
    }

    public static BatchImortQueue getInstance()
    {
        return BatchImortQueue.instance;
    }

    public void produce(DispatchPlan vo) throws InterruptedException {

        queue.put(vo);
    }

    public void produce(List<DispatchPlan> lst) throws InterruptedException {

        for (DispatchPlan vo: lst)
        {
            produce(vo);
        }
    }

    public DispatchPlan get() throws InterruptedException {
        return queue.take();
    }


}
