package com.guiji.process.core;

import com.guiji.process.core.message.CmdMessageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhujy on 2018/11/17.
 */
@Service
public class ProcessMsgHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final  ProcessMsgHandler instance = new ProcessMsgHandler();

    private ProcessMsgHandler() {

    }

    public static ProcessMsgHandler getInstance()
    {
        return instance;
    }

    public void add(CmdMessageVO cmdMessageVO){
        try {
            CmdMessageQueue.getInstance().produce(cmdMessageVO);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public void add(List<CmdMessageVO> cmdMessageVOs){

        for (CmdMessageVO cmdMessageVO:cmdMessageVOs) {
            add(cmdMessageVO);
        }
    }
}
