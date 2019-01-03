package com.guiji.dispatch.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    @Bean
    public Queue ModularLogsMQ() {
        return new Queue("dispatch.ModularLogs");
    }
    
    @Bean
    public Queue MessageMQ() {
        return new Queue("dispatch.MessageMQ");
    }
    
    @Bean
    public Queue SuccessPhoneMQ() {
        return new Queue("dispatch.SuccessPhoneMQ");
    }
    
}
