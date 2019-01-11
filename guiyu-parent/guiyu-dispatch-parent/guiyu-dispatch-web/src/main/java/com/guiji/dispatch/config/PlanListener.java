package com.guiji.dispatch.config;

import com.guiji.dispatch.service.IPhonePlanQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by ty on 2019/1/7.
 */
@Component
public class PlanListener implements ApplicationRunner {
    static Logger logger = LoggerFactory.getLogger(PlanListener.class);
    @Autowired
    private IPhonePlanQueueService phonePlanQueueService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(() -> {
            try {
                phonePlanQueueService.execute();
            } catch (Exception e) {
                logger.error("PlanListener:" + e.getMessage());
            }
        }).start();
    }
}
