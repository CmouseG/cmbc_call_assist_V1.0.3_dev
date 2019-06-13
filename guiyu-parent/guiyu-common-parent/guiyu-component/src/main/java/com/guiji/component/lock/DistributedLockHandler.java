package com.guiji.component.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class DistributedLockHandler
{
    private static final Logger logger = LoggerFactory.getLogger(DistributedLockHandler.class);
    private final static long LOCK_EXPIRE = 30* 1000L; // 单个业务持有锁的时间30s，防止死锁
    private final static long LOCK_TRY_INTERVAL = 20L; // 默认20ms尝试一次
    private final static long LOCK_TRY_TIMEOUT = 5 * 1000L; // 默认尝试5s

    @Autowired
    private RedisTemplate redisTemplate;

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
                //setIfAbsent : redis命令setnx(), 作用：SET if Not Exists，其主要有两个参数 setnx(key, value)。该方法是原子的，如果 key 不存在，则设置当前 key 成功，返回 1；如果当前 key 已经存在，则设置当前 key 失败，返回 0
                //在分布式锁的使用中比去校验key是否存在 安全的多（因为原子性）
                long startTimeTmp = System.currentTimeMillis();
                if (setIfAbsent(lock.getName(), lock.getValue(), lockExpireTime/1000))
                {
                    logger.debug(Thread.currentThread().getName() + " : get lock[" + lock.getName() + "]");
                    //logger.info(Thread.currentThread().getName() + " : get lock[" + lock.getName() + "]"+(System.currentTimeMillis()-startTimeTmp)+"毫秒");
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
            redisTemplate.delete(lock.getName());
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

        if (redisTemplate.hasKey(lock.getName()))
        {
            return true;
        }

        return false;
    }


    /**
     * @param key
     * @param value
     * @param seconds 超时时间，秒为单位
     * @Description: setIfAbsent升级版，加了超时时间
     * @Author: Gong Yongwei
     * @Date: 2018/12/12 9:21
     * @return: boolean
     */
    private boolean setIfAbsent(String key, String value, Long seconds) {

        StringRedisSerializer ss = new StringRedisSerializer();
        List<Object> keys = new ArrayList<Object>();
        keys.add(key);
        Object[] args = {value, seconds.toString()};
        redisTemplate.setKeySerializer(ss);
        redisTemplate.setValueSerializer(ss);
        return (Boolean) redisTemplate.<Boolean>execute(RedisLockLuaScripts.newSetIfAbsentScript, Collections.singletonList(key), value, seconds.toString());
    }



//    private boolean setIfAbsent(final String key, final String value, final long exptime) {
//        Boolean b = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                StringRedisSerializer valueSerializer = (StringRedisSerializer) redisTemplate.getValueSerializer();
//                StringRedisSerializer keySerializer = (StringRedisSerializer) redisTemplate.getKeySerializer();
//                Object obj = connection.execute("set", keySerializer.serialize(key), valueSerializer.serialize(value), SafeEncoder.encode("NX"), SafeEncoder.encode("EX"), Protocol.toByteArray(exptime));
//                return obj != null;
//            }
//        });
//        return b;
//    }
}
