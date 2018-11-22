package com.guiji.process.agent.core;

import com.guiji.process.core.IProcessCmdHandler;
import com.guiji.process.core.ProcessMsgReadHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ImAgentQueueListener implements ApplicationRunner {


    @Autowired
    private IProcessCmdHandler handler;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        new Thread(() -> {
            new ProcessMsgReadHandler().run(handler);
        }).start();

    }
}
