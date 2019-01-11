package com.guiji.dispatch.config;

import com.guiji.dispatch.service.IPhonePlanQueueService;
import com.guiji.dispatch.service.IResourcePoolService;
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
    @Autowired
    private IResourcePoolService resourcePoolService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(() -> {
            try {
                //初始化系统资源
                resourcePoolService.initResourcePool();
                //服务启动先把队列数据回退，重新按用户分配后再开始获取队列计划
                resourcePoolService.distributeByUser();
                //拉去拨打计划进队列
                phonePlanQueueService.execute();
            } catch (Exception e) {
                logger.error("PlanListener:" + e.getMessage());
            }
        }).start();
    }
}
