package com.guiji.process.server.service.impl;

import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.ProcessInstanceVO;
import com.guiji.process.server.model.CmdMessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
public class DeviceMsgHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private DeviceMsgHandler() {

    }

    public void add(CmdMessageVO cmdMessageVO){
        try {
            CmdMessageQueue.getInstance().produce(cmdMessageVO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void add(List<CmdMessageVO> cmdMessageVOs){

        for (CmdMessageVO cmdMessageVO:cmdMessageVOs) {
            add(cmdMessageVO);
        }
    }

}
