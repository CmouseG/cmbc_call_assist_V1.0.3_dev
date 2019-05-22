package com.guiji.cfg;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version V1.0
 * @ClassName: MQConfig
 * @Description: 后台模块异步回调，消息队列
 */
@Configuration
public class ThirdApiMqConfig {
    //TTS合成回调
    public static final String NOTIFY_QUEUE = "thirdApi.notify";

    public static final String BATCH_PLAN_TASK = "thirdApi.batchplan.list.task";

    public static final String NOTIFY_UPDATE_USER = "third.api.user";

    public static final String NOTIFY_UPDATE_USER_KEY = "third.api.key";

    @Bean
    public Queue notifyQueue() {
        return new Queue(NOTIFY_QUEUE);
    }

    @Bean
    public Queue batchPlanQueue() {
        return new Queue(BATCH_PLAN_TASK);
    }

    @Bean
    public Queue notifyUpdateUserQueue() {
        return new Queue(NOTIFY_UPDATE_USER);
    }

    @Bean
    public Queue updateUserKeyQueue() {
        return new Queue(NOTIFY_UPDATE_USER_KEY);
    }
}
