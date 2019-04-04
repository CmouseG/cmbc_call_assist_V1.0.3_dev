package com.guiji.ccmanager.scheduler;

import com.guiji.ccmanager.service.ReportSchedulerService;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: 黎阳  废弃
 * @Date: 2018/12/6 0006 11:08
 * @Description:
 */
@Component
@Slf4j
//@EnableScheduling
public class ReportScheduler {

    @Autowired
    ReportSchedulerService reportSchedulerService;
    @Autowired
    DistributedLockHandler distributedLockHandler;

//    @Scheduled(cron = "0 5 0 * * ?") // 凌晨5分执行
//    @Scheduled(cron = "0/5 * * * * ?") // 测试
    public void reportCallDayScheduler(){

        log.info("----------- start reportCallDayScheduler -----------");
        Lock lock = new Lock("ReportScheduler.reportCallDayScheduler", "ReportScheduler.reportCallDayScheduler");
        if (distributedLockHandler.tryLock(lock)) { // 默认锁设置

            try {
                reportSchedulerService.reportCallDayScheduler();
            } catch (Exception e) {
                log.info("reportCallDayScheduler error", e);
            } finally {
                distributedLockHandler.releaseLock(lock);// 释放锁
            }
        }

        log.info("----------- end reportCallDayScheduler -----------");

    }

//      @Scheduled(cron = "0 10 * * * ?") // 每个小时10分钟运行一次
//    @Scheduled(cron = "0/5 * * * * ?") // 测试
    public void reportCallHourScheduler(){

        log.info("----------- start reportCallHourScheduler -----------");
          Lock lock = new Lock("ReportScheduler.reportCallHourScheduler", "ReportScheduler.reportCallHourScheduler");
        if (distributedLockHandler.tryLock(lock)) { // 默认锁设置

            try {
                reportSchedulerService.reportCallHourScheduler();
            } catch (Exception e) {
                log.info("reportCallHourScheduler error", e);
            } finally {
                distributedLockHandler.releaseLock(lock);// 释放锁
            }
        }

        log.info("----------- end reportCallHourScheduler -----------");

    }

//    @Scheduled(cron = "0 1 0 * * ?") //凌晨的时候清空report_call_today
//    @Scheduled(cron = "0/5 * * * * ?") // 测试
    public void reportCallTodayTruncate(){

        log.info("----------- start reportCallTodayTruncate -----------");
        Lock lock = new Lock("ReportScheduler.reportCallTodayTruncate", "ReportScheduler.reportCallTodayTruncate");
        if (distributedLockHandler.tryLock(lock)) { // 默认锁设置

            try {
                reportSchedulerService.reportCallTodayTruncate();
            } catch (Exception e) {
                log.info("reportCallTodayTruncate error", e);
            } finally {
                distributedLockHandler.releaseLock(lock);// 释放锁
            }
        }

        log.info("----------- end reportCallTodayTruncate -----------");

    }
}
