package com.guiji.component.lock;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class DistributedLockHandler
{
	private static final Logger logger = LoggerFactory.getLogger(DistributedLockHandler.class);
	private final static long LOCK_EXPIRE = 30 * 1000L; // 单个业务持有锁的时间30s，防止死锁
	private final static long LOCK_TRY_INTERVAL = 20L; // 默认20ms尝试一次
	private final static long LOCK_TRY_TIMEOUT = 5 * 1000L; // 默认尝试5s

	@Autowired
	private StringRedisTemplate template;

	/**
	 * 尝试获取全局锁
	 * 
	 * @param lock
	 * @return
	 */
	public boolean tryLock(Lock lock)
	{
		return getLock(lock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
	}

	public boolean tryLock(Lock lock, long timeout)
	{
		return getLock(lock, timeout, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
	}

	public boolean tryLock(Lock lock, long timeout, long tryInterval)
	{
		return getLock(lock, timeout, tryInterval, LOCK_EXPIRE);
	}

	public boolean tryLock(Lock lock, long timeout, long tryInterval, long lockExpireTime)
	{
		return getLock(lock, timeout, tryInterval, lockExpireTime);
	}

	/**
	 * 操作redis获取全局锁
	 * 
	 * @param lock
	 * @param timeout
	 * @param tryInterval
	 * @param lockExpireTime
	 * @return
	 */
	public boolean getLock(Lock lock, long timeout, long tryInterval, long lockExpireTime)
	{
		try
		{
			if (StringUtils.isEmpty(lock.getName()) || StringUtils.isEmpty(lock.getValue())) // 对lock进行校验
			{
				return false;
			}
			long startTime = System.currentTimeMillis();
			while (true)
			{
				if (!template.hasKey(lock.getName()))
				{
					template.opsForValue().set(lock.getName(), lock.getValue(), lockExpireTime, TimeUnit.MILLISECONDS);
					logger.debug(Thread.currentThread().getName() + " : get lock[" + lock.getName() + "]");
					return true;
				} 
				else
				{
					logger.debug(Thread.currentThread().getName() + " : ----> lock[" + lock.getName() + "] is exist!!!");
				}
				if (System.currentTimeMillis() - startTime > timeout)
				{
					return false;
				}
				Thread.sleep(tryInterval);
			}
		} catch (Exception e)
		{
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 释放锁
	 */
	public void releaseLock(Lock lock)
	{
		if (!StringUtils.isEmpty(lock.getName()))
		{
			logger.debug(Thread.currentThread().getName() + " : delete lock[" + lock.getName() + "]");
			template.delete(lock.getName());
		}
	}

	/**
	 * 判断是否锁住
	 * @param lock
	 * @return
	 */
	public boolean isLockExist(Lock lock)
	{
		if (StringUtils.isEmpty(lock.getName()) || StringUtils.isEmpty(lock.getValue())) // 对lock进行校验
		{
			return false;
		}

		if (template.hasKey(lock.getName()))
		{
			return true;
		}

		return false;
	}

}
