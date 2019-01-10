package com.guiji.dispatch.batchimport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchImportQueueListener implements ApplicationRunner {


    @Autowired
    private IBatchImportRecordHandler handler;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        new Thread(() -> {
            new BatchImportQueueReadHandler().run(handler);
        }).start();

    }
}
