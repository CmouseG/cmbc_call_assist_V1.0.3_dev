package com.guiji.process.server.config;

import com.guiji.process.core.IProcessCmdHandler;
import com.guiji.process.core.ProcessServerMsgReadHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ImServerQueueListener implements ApplicationRunner {


    @Autowired
    private IProcessCmdHandler handler;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        new Thread(() -> {
            new ProcessServerMsgReadHandler().run(handler);
        }).start();

    }
}
