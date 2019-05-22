package com.guiji.delay;

import com.guiji.cfg.RedisKey;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 延迟队列服务，
 * 负责将消息丢到延迟队列
 */
@Component
public class RedisDelayService {

    private final static Long[] EXCUTE_TIME = {0L, 300L, 300L, 3600L, 3600L};

    @Autowired
    RedissonClient redissonClient;

    public RDelayedQueue<DelayMessage> getQueue() {
        RBlockingQueue<DelayMessage> blockingQueue = redissonClient.getBlockingQueue(RedisKey.NOTIFY_MESSAGE);

        return redissonClient.getDelayedQueue(blockingQueue);
    }

    /**
     * 添加到队列信息
     *
     * @param delayMessage
     */
    public void putToQueue(DelayMessage delayMessage) {
        if (delayMessage.getCurrNum() == null) {
            delayMessage.setCurrNum(0);
            getQueue().offer(delayMessage, 0, TimeUnit.SECONDS);
        } else {
            delayMessage.setCurrNum(delayMessage.getCurrNum() + 1);

            //超过通知次数，放弃
            if (delayMessage.getCurrNum() > 4) {
                return;
            }
            getQueue().offer(delayMessage, EXCUTE_TIME[delayMessage.getCurrNum()], TimeUnit.SECONDS);
        }
    }

}
