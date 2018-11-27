package com.guiji.process.server.config;

import com.guiji.process.server.core.ImServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ImServerListener implements ApplicationRunner {


    @Autowired
    ImServer imServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        int port = 18082;
        new Thread(() -> {
            imServer.run(port);
        }).start();

    }
}
