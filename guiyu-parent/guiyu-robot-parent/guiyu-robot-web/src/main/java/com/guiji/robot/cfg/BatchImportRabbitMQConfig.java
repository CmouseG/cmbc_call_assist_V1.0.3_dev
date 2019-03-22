package com.guiji.robot.cfg;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchImportRabbitMQConfig {


    @Bean("batchImportRabbitFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(1);  //设置线程数
        factory.setMaxConcurrentConsumers(1); //最大线程数

//        ExecutorService service= Executors.newFixedThreadPool(100);
//        factory.setTaskExecutor(service);
//        factory.setPrefetchCount(5);

        configurer.configure(factory, connectionFactory);
        return factory;
    }
}

