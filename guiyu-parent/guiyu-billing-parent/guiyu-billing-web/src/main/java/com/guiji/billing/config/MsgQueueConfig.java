package com.guiji.billing.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MsgQueueConfig {
    @Bean
    public Queue acctCharging() {
        return new Queue("billing.ACCTCHARGING");
    }

    @Bean
    public Queue chargingTermNotify() {
        return new Queue("billing.CHARGINGTERMNOTIFY");
    }

}
