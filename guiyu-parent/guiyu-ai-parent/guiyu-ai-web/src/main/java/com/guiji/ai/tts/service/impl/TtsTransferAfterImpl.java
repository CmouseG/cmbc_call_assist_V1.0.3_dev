package com.guiji.ai.tts.service.impl;

import com.guiji.ai.tts.vo.TtsSubVO;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by ty on 2018/11/13.
 */
public class TtsTransferAfterImpl {

    private static final TtsTransferAfterImpl INSTANCE = new TtsTransferAfterImpl();

    private Productor productor = null;

    private Consummer consummer = null;


    private TtsTransferAfterImpl() {

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        productor = new Productor();

        consummer = new Consummer(productor);

        executorService.execute(consummer);
    }

    public static TtsTransferAfterImpl getInstance()
    {
        return INSTANCE;
    }

    public void add(TtsSubVO ttsSubVO){
        try {
            productor.produce(ttsSubVO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public class Productor {

        private BlockingQueue<TtsSubVO> queue = null;

        protected Productor()
        {
            queue = new LinkedBlockingQueue<TtsSubVO>();
        }

        public void produce(TtsSubVO ttsSubVO) throws InterruptedException {
            queue.put(ttsSubVO);
        }

        public TtsSubVO get() throws InterruptedException {
           return queue.take();
        }
    }


    public class Consummer implements Runnable {

        private Productor productor = null;

        public Consummer(Productor productor)
        {
            this.productor = productor;
        }
        @Override
        public void run() {

            Long start = null;
            Long end = null;
            while (true)
            {
                try {

                    System.out.println("我处理完了：");
                    TtsSubVO ttsSubVO = this.productor.get();



                    if(ttsSubVO == null)
                    {
                        System.out.println("我处理完了："+(end - start));
                        Thread.sleep(2000);
                    }

                    if(ttsSubVO != null && start == null)
                    {
                        start = System.currentTimeMillis();
                    }

                    System.out.println("got a new subVO:" +ttsSubVO);

                    if(ttsSubVO != null)
                    {
                        end = System.currentTimeMillis();
                    }
                    // do sth db
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String [] args)
    {

        Long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            TtsSubVO vo = new TtsSubVO();
            vo.setBusId("busId");
            vo.setModel("module");
            vo.setContent("这是一段文本"+i);

            TtsTransferAfterImpl.getInstance().add(vo);
        }

        Long end = System.currentTimeMillis();

        System.out.println("我再这里："+(end - start));


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100; i++) {
            TtsSubVO vo = new TtsSubVO();
            vo.setBusId("busId");
            vo.setModel("module");
            vo.setContent("这个是其他的"+i);

            TtsTransferAfterImpl.getInstance().add(vo);
        }

    }




}
