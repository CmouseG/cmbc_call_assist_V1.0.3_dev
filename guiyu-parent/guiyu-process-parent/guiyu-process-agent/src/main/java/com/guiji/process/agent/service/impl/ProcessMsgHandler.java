package com.guiji.process.agent.service.impl;

import com.guiji.process.core.message.CmdMessageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhujy on 2018/11/17.
 */
@Service
public class ProcessMsgHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ProcessMsgHandler INSTANCE = new ProcessMsgHandler();

    private Productor productor = null;

    private Consummer consummer = null;


    private ProcessMsgHandler() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        productor = new Productor();
        consummer = new Consummer(productor);

        executorService.execute(consummer);
    }

    public static ProcessMsgHandler getInstance()
    {
        return INSTANCE;
    }

    public void add(CmdMessageVO cmdMessageVO){
        try {
            productor.produce(cmdMessageVO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void add(List<CmdMessageVO> cmdMessageVOs){

        for (CmdMessageVO cmdMessageVO:cmdMessageVOs) {
            add(cmdMessageVO);
        }
    }


    public class Productor {

        private BlockingQueue<CmdMessageVO> queue = null;

        protected Productor()
        {
            queue = new LinkedBlockingQueue<CmdMessageVO>();
        }

        public void produce(CmdMessageVO deviceMsgVO) throws InterruptedException {
            queue.put(deviceMsgVO);
        }

        public CmdMessageVO get() throws InterruptedException {
            return queue.take();
        }
    }


    public class Consummer implements Runnable {

        private Productor productor = null;

        @Autowired
        private ProcessCmdHandler handler;

        public Consummer(Productor productor)
        {
            this.productor = productor;
        }


        @Override
        public void run() {
            CmdMessageVO cmdMessageVO = null;
            while (true)
            {
                try {
                    cmdMessageVO = this.productor.get();

                    if(cmdMessageVO == null)
                    {
                        continue;
                    }

                    handler.excute(cmdMessageVO);

                } catch (Exception e) {
                    logger.error("Consummer:" + cmdMessageVO,e);
                }
            }
        }
    }
}
