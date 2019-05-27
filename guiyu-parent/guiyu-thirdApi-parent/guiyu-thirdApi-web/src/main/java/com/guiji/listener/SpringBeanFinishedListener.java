package com.guiji.listener;

import com.guiji.cfg.RedisKey;
import com.guiji.delay.DelayMessage;
import com.guiji.delay.RedisDelayService;
import com.guiji.utils.HttpClientUtil;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 处理延迟队列中的数据
 */
@Component
public class SpringBeanFinishedListener implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SpringBeanFinishedListener.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    RedisDelayService redisDelayService;

    /**
     * 监听blockingqueue中的数据，执行回调通知
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {

        RBlockingQueue<DelayMessage> blockingQueue = redissonClient.getBlockingQueue(RedisKey.NOTIFY_MESSAGE);

        new Thread(() -> {
            while (true) {
                logger.info("开始执行回调操作!");
                DelayMessage delayMessage = null;
                try {
                    delayMessage = blockingQueue.take();

                    logger.info("start call back , url : {}, body : {}", delayMessage.getNotifyUrl(), delayMessage.getBody());

                    HttpClientUtil.doPostJson(delayMessage.getNotifyUrl(), delayMessage.getBody());
                } catch (Exception e) {
                    //将错误的消息体写到日志中
                    logger.error("回调请求失败：{}", delayMessage.getBody());
                    //添加到延时队列中
                    if (delayMessage != null) {
                        redisDelayService.putToQueue(delayMessage);
                    } else {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            logger.info("sleep encounter error : {}", ex.getMessage());
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }).start();


    }
}
