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
    
    @Bean
    public Queue filedata() {
        return new Queue("dispatch.filedata");
    }
    @Bean
    public Queue callBackEvent() {
        return new Queue("dispatch.CallBackEvent");
    }
    @Bean
    public Queue pushPhonesRecords() {
        return new Queue("dispatch.PushPhonesRecords");
    }
    @Bean
    public Queue thirdApiData() {
        return new Queue("dispatch.thirdApiData");
    }
    
    @Bean
    public Queue blackListData() {
        return new Queue("dispatch.blackListData");
    }
    
}
