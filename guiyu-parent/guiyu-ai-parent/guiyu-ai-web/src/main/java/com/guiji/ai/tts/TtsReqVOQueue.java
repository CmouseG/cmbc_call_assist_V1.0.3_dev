package com.guiji.ai.tts;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.guiji.ai.vo.TtsReqVO;

/**
 * Created by ty on 2018/11/21.
 */
public class TtsReqVOQueue {

    private static final TtsReqVOQueue instance = new TtsReqVOQueue();

    private BlockingQueue<TtsReqVO> queue = null;

    private TtsReqVOQueue()
    {
        queue = new LinkedBlockingQueue<TtsReqVO>();
    }

    public static TtsReqVOQueue getInstance()
    {
        return TtsReqVOQueue.instance;
    }

    public void produce(TtsReqVO ttsReqVO) throws InterruptedException {

        queue.put(ttsReqVO);
    }

    public TtsReqVO get() throws InterruptedException {
        return queue.take();
    }


}
