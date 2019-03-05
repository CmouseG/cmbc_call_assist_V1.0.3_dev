package com.guiji.job;

import com.guiji.service.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class JobSchedule {
    @Autowired
    AgentService agentService;
    @Scheduled(cron = "${job.initCallCenter}")
    public void clearTempJob(){
        log.info("定时任务initCallCenter开始，从数据库中加载callcenter的数据组装成xml");
        agentService.initCallcenter();
        log.info("定时任务initCallCenter结束，从数据库中加载callcenter的数据组装成xml");
    }
}
