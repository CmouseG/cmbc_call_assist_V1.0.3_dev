package com.guiji.ccmanager.scheduler;

import com.guiji.ccmanager.service.ReportSchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/6 0006 11:08
 * @Description:
 */
@Component
@Slf4j
@EnableScheduling
public class ReportScheduler {

    @Autowired
    ReportSchedulerService reportSchedulerService;

    @Scheduled(cron = "0 30 0 * * ?") // 凌晨30分执行
//    @Scheduled(cron = "0/5 * * * * ?") // 测试
    public void reportCallDayScheduler(){

        log.info("----------- start reportCallDayScheduler -----------");
        reportSchedulerService.reportCallDayScheduler();
        log.info("----------- end reportCallDayScheduler -----------");

    }

      @Scheduled(cron = "0 10 * * * ?") // 每个小时10分钟运行一次
//    @Scheduled(cron = "0/5 * * * * ?") // 测试
    public void reportCallHourScheduler(){

        log.info("----------- start reportCallHourScheduler -----------");
        reportSchedulerService.reportCallHourScheduler();
        log.info("----------- end reportCallHourScheduler -----------");

    }

}
