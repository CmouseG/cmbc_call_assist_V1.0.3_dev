package com.guiji.process.server.config;

import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.server.core.ImServer;
import com.guiji.process.server.model.CmdMessageQueue;
import com.guiji.process.server.service.impl.DeviceCmdHandler;
import com.guiji.process.server.service.impl.DeviceMsgReadHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ImServerQueueListener implements ApplicationRunner {


    @Autowired
    private DeviceCmdHandler handler;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        new Thread(() -> {
            new DeviceMsgReadHandler().run(handler);
        }).start();

    }
}
