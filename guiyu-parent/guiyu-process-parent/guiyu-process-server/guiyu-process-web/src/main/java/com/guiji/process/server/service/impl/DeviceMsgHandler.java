package com.guiji.process.server.service.impl;

import com.guiji.process.vo.DeviceMsgVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhujy on 2018/11/17.
 */
@Service
public class DeviceMsgHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final DeviceMsgHandler INSTANCE = new DeviceMsgHandler();

    private Productor productor = null;

    private Consummer consummer = null;


    private DeviceMsgHandler() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        productor = new Productor();
        consummer = new Consummer(productor);

        executorService.execute(consummer);
    }

    public static DeviceMsgHandler getInstance()
    {
        return INSTANCE;
    }

    public void add(DeviceMsgVO deviceMsgVO){
        try {
            productor.produce(deviceMsgVO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public class Productor {

        private BlockingQueue<DeviceMsgVO> queue = null;

        protected Productor()
        {
            queue = new LinkedBlockingQueue<DeviceMsgVO>();
        }

        public void produce(DeviceMsgVO deviceMsgVO) throws InterruptedException {
            queue.put(deviceMsgVO);
        }

        public DeviceMsgVO get() throws InterruptedException {
           return queue.take();
        }
    }


    public class Consummer implements Runnable {

        private Productor productor = null;

        @Autowired
        private DeviceCmdHandler handler;

        public Consummer(Productor productor)
        {
            this.productor = productor;
        }


        @Override
        public void run() {
            DeviceMsgVO deviceMsgVO = null;
            while (true)
            {
                try {
                    deviceMsgVO = this.productor.get();

                    if(deviceMsgVO == null)
                    {
                        continue;
                    }

                    handler.excute(deviceMsgVO);

                } catch (Exception e) {
                    logger.error("Consummer:" + deviceMsgVO,e);
                }
            }
        }
    }
}
