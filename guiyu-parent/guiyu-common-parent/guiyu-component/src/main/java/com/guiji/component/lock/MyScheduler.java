package com.guiji.component.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
 
	@Autowired
	DistributedLockHandler distributedLockHandler;	
 
//	@Scheduled(cron = "0/2 11 18 * * ?")
	@Scheduled(fixedRate = 1000)
    public void task() throws InterruptedException {
		logger.info(Thread.currentThread().getName()+" 开始执行定时任务！");
    	Lock lock=new Lock("myScheduled","lockvalue");
    	if(distributedLockHandler.tryLock(lock)){
    		logger.info(Thread.currentThread().getName()+" 获得锁！！！！");
    		logger.info(Thread.currentThread().getName()+" 开始执行业务...");
    		Thread.sleep(1000);
    		logger.info(Thread.currentThread().getName()+" 执行业务结束...");
    		distributedLockHandler.releaseLock(lock);
    		logger.info(Thread.currentThread().getName()+" 释放锁成功！！...");
    	}
    	else{
    		logger.info(Thread.currentThread().getName()+" 获取锁失败！！！");
    	}
    }
}
