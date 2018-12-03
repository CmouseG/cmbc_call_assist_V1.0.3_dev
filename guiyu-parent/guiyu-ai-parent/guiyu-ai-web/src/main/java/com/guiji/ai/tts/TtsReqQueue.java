package com.guiji.ai.tts;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.guiji.ai.vo.TtsReqVO;

/**
 * Created by ty on 2018/11/21.
 */
public class TtsReqQueue {

    private static final TtsReqQueue instance = new TtsReqQueue();

    private BlockingQueue<TtsReqVO> queue = null;

    private TtsReqQueue()
    {
        queue = new LinkedBlockingQueue<TtsReqVO>();
    }

    public static TtsReqQueue getInstance()
    {
        return TtsReqQueue.instance;
    }

    public void produce(TtsReqVO ttsReqVO) throws InterruptedException {

        queue.put(ttsReqVO);
    }

    public TtsReqVO get() throws InterruptedException {
        return queue.take();
    }


}
