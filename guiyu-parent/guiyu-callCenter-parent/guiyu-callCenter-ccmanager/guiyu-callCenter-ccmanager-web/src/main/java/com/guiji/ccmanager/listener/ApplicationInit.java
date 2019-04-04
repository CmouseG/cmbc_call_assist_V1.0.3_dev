package com.guiji.ccmanager.listener;

import com.guiji.ccmanager.scheduler.ReportScheduler;
import com.guiji.ccmanager.service.ReportSchedulerService;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationInit {

    @Autowired
    ReportSchedulerService reportSchedulerService;
    @Autowired
    DistributedLockHandler distributedLockHandler;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        log.info("----------application init start check----------");

        /*Lock lock = new Lock("ApplicationInit.isTruncateSuccess", "ApplicationInit.isTruncateSuccess");
        try {
            if (distributedLockHandler.tryLock(lock)) {
                if (!reportSchedulerService.isTruncateSuccess()) {  //假如清空today表失败了。将之前几天的数据删除掉
                    log.info("----------truncate task has not done suceess----------");
                    reportSchedulerService.reportCallTodayTruncateBefore();
                }
            }
        }catch (Exception e){
            log.error("检查清空当天统计表失败",e);
        }finally {
            distributedLockHandler.releaseLock(lock);// 释放锁
        }*/

        Lock lockDay = new Lock("ApplicationInit.isDaySheduleSuccess", "ApplicationInit.isDaySheduleSuccess");
        if (distributedLockHandler.tryLock(lockDay)) {

            try {
                if (!reportSchedulerService.isDaySheduleSuccess()) {  //假如统计天表失败了。则重新统计昨天的数据
                    log.info("----------day count task has not done suceess----------");
                    reportSchedulerService.reportCallDayScheduler();
                }
            } catch (Exception e) {
                log.error("检查清空当天统计表失败", e);
            } finally {
                distributedLockHandler.releaseLock(lockDay);// 释放锁
            }
        }


        log.info("----------application init end check----------");
    }

}
