package com.guiji.ai.tts;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.guiji.ai.vo.TtsReqVO;

/**
 * Created by ty on 2018/11/21.
 */
public class JumpTaskQueue {

    private static final JumpTaskQueue instance = new JumpTaskQueue();

    private BlockingQueue<TtsReqVO> queue = null;

    private JumpTaskQueue()
    {
        queue = new LinkedBlockingQueue<TtsReqVO>();
    }

    public static JumpTaskQueue getInstance()
    {
        return JumpTaskQueue.instance;
    }

    public void produce(TtsReqVO ttsReqVO) throws InterruptedException {

        queue.put(ttsReqVO);
    }

    public TtsReqVO get() throws InterruptedException {
        return queue.take();
    }

    public Boolean isEmpty(){
    	return queue.isEmpty();
    }
}
