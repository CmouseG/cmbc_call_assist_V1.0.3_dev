package com.guiji.component.lock;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;


@Component
public class DistributedLockHandler {
	private static final Logger logger = LoggerFactory.getLogger(DistributedLockHandler.class);
	private final static long LOCK_EXPIRE = 30 * 1000L;// 单个业务持有锁的时间30s，防止死锁
	private final static long LOCK_TRY_INTERVAL = 30L;// 默认30ms尝试一次
	private final static long LOCK_TRY_TIMEOUT = 20 * 1000L;// 默认尝试20s
	
	@Autowired
	private StringRedisTemplate template;
	
	/**
	 * 尝试获取全局锁
	 * @param lock
	 * @return
	 */
	public boolean tryLock(Lock lock) {
		return getLock(lock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
	}
	
	public boolean tryLock(Lock lock, long timeout) {
		return getLock(lock, timeout, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
	}
	
	public boolean tryLock(Lock lock, long timeout, long tryInterval) {
		return getLock(lock, timeout, tryInterval, LOCK_EXPIRE);
	}
	
	public boolean tryLock(Lock lock, long timeout, long tryInterval, long lockExpireTime) {
		return getLock(lock, timeout, tryInterval, lockExpireTime);
	}

	/**
	 * 操作redis获取全局锁
	 * @param lock
	 * @param timeout
	 * @param tryInterval
	 * @param lockExpireTime
	 * @return
	 */
	public boolean getLock(Lock lock, long timeout, long tryInterval, long lockExpireTime) {
		try {
			if (StringUtils.isEmpty(lock.getName()) || StringUtils.isEmpty(lock.getValue())) {
				return false;
			}
			long startTime = System.currentTimeMillis();
			do {
				if (!template.hasKey(lock.getName())) {
					ValueOperations<String, String> ops = template.opsForValue();
					ops.set(lock.getName(), lock.getValue(), lockExpireTime, TimeUnit.MILLISECONDS);
					return true;
				} else {// 存在锁
					logger.debug("lock is exist!！！");
				}
				if (System.currentTimeMillis() - startTime > timeout) {// 尝试超过了设定值之后直接跳出循环
					return false;
				}
				Thread.sleep(tryInterval);
			} while (template.hasKey(lock.getName()));
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			return false;
		}
		return false;
	}
	
	/**
	 * 释放锁
	 */
	public void releaseLock(Lock lock) {
		if (!StringUtils.isEmpty(lock.getName())) {
			template.delete(lock.getName());
		}
	}
	
}
