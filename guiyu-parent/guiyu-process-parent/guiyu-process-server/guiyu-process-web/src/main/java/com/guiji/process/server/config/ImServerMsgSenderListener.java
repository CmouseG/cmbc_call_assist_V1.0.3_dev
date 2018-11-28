package com.guiji.process.server.config;

import com.guiji.process.core.IProcessCmdHandler;
import com.guiji.process.core.ProcessMsgReadHandler;
import com.guiji.process.server.handler.ProcessMsgSendHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ImServerMsgSenderListener implements ApplicationRunner {

    public void run(ApplicationArguments args) throws Exception {

        new Thread(() -> {
            new ProcessMsgSendHandler().run();
        }).start();

    }
}
