package com.guiji.dispatch.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModularLogsMQ {
    @Bean
    public Queue PublishBotstenceSellbotMessage() {
        return new Queue("dispatch.ModularLogs");
    }
}
