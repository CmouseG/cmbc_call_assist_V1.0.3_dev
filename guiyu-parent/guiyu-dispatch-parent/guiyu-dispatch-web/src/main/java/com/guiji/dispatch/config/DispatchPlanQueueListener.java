package com.guiji.dispatch.config;

import com.guiji.dispatch.bean.DispatchPlanReadHandler;
import com.guiji.dispatch.state.DispatchPlanCleanHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DispatchPlanQueueListener implements ApplicationRunner {


    @Autowired
    private DispatchPlanCleanHandler handler;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        new Thread(() -> {
            new DispatchPlanReadHandler().run(handler);
        }).start();

    }
}
