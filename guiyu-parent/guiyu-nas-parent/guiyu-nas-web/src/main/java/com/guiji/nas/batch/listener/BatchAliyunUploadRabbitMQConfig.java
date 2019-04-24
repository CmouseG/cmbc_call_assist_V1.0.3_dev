package com.guiji.nas.batch.listener;


import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.guiji.nas.util.OssUtil;

/**
 * @version V1.0
 * @ClassName: BatchUploadRabbitMQConfig
 * @Description: 异步多线程配置
 */
@Configuration
public class BatchAliyunUploadRabbitMQConfig
{
	
    @Bean("batchUploadRabbitFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(50); // 设置线程数
        factory.setMaxConcurrentConsumers(200); // 最大线程数

        configurer.configure(factory, connectionFactory);
        return factory;
    }
    
}