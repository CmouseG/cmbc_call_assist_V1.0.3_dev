package com.guiji.sysoperalog.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.guiji.guiyu.sysoperalog.dao.entity.SysUserAction;

public class SysOpreaLogQueue
{
    private static final SysOpreaLogQueue instance = new SysOpreaLogQueue();

    private BlockingQueue<SysUserAction> queue = null;

    private SysOpreaLogQueue()
    {
        queue = new LinkedBlockingQueue<SysUserAction>();
    }

    public static SysOpreaLogQueue getInstance()
    {
        return SysOpreaLogQueue.instance;
    }

    public void produce(SysUserAction sysUserAction) throws InterruptedException {

        queue.put(sysUserAction);
    }

    public SysUserAction get() throws InterruptedException {
        return queue.take();
    }
}
