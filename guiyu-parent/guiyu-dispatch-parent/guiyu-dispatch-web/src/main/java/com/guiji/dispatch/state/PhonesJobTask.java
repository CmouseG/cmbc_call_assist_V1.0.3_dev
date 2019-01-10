package com.guiji.dispatch.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.dispatch.util.Constant;

@RestController
public class PhonesJobTask {

	private static final Logger logger = LoggerFactory.getLogger(PhonesJobTask.class);

	@Autowired
	DistributedLockHandler distributedLockHandler;
	@Autowired
	private IphonesThread phoneThread;
	@PostMapping("execute")
	public void execute() {
		Lock lock = new Lock("PhonesJobTask", "PhonesJobTask");
		try {
			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
				phoneThread.execute();
			}
		} catch (Exception e) {
			logger.info("error", e);
		} finally {
			distributedLockHandler.releaseLock(lock);
		}
	}
}
