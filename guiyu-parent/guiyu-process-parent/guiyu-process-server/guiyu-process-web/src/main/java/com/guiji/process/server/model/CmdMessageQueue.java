package com.guiji.process.server.model;

import com.guiji.process.core.message.CmdMessageVO;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ty on 2018/11/21.
 */
public class CmdMessageQueue {

    private static final CmdMessageQueue instance = new CmdMessageQueue();

    private BlockingQueue<CmdMessageVO> queue = null;

    private CmdMessageQueue()
    {
        queue = new LinkedBlockingQueue<CmdMessageVO>();
    }

    public static CmdMessageQueue getInstance()
    {
        return CmdMessageQueue.instance;
    }

    public void produce(CmdMessageVO deviceMsgVO) throws InterruptedException {
        queue.put(deviceMsgVO);
    }

    public CmdMessageVO get() throws InterruptedException {
        return queue.take();
    }


}
