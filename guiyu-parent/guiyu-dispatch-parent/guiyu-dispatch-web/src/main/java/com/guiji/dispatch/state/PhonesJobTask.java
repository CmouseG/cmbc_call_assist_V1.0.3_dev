//package com.guiji.dispatch.state;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.guiji.component.lock.DistributedLockHandler;
//import com.guiji.component.lock.Lock;
//
////@RestController
//@Component
//public class PhonesJobTask {
//
//	private static final Logger logger = LoggerFactory.getLogger(PhonesJobTask.class);
//
//	@Autowired
//	DistributedLockHandler distributedLockHandler;
//	@Autowired
//	private IphonesThread phoneThread;
//	
//	
//	@Scheduled(cron = "0 0/1 * * * ?")
//	public void execute() {
//		Lock lock = new Lock("PhonesJobTask", "PhonesJobTask");
//		try {
//			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
//				phoneThread.execute();
//			}
//		} catch (Exception e) {
//			logger.info("error", e);
//		} finally {
//			distributedLockHandler.releaseLock(lock);
//		}
//	}
//}
