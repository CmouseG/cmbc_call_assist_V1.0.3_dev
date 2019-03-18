package com.guiji.dispatch.pushcallcenter.config;


import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version V1.0
 * @ClassName: BatchImportThreadAsyncConfig
 * @Description: 异步多线程配置
 */
@Configuration
public class CallPhoneRabbitMQConfig
{
    @Bean("successMqRabbitFactory")
    public SimpleRabbitListenerContainerFactory successMqRabbitFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(8);  //设置线程数
        factory.setMaxConcurrentConsumers(50); //最大线程数

        //        ExecutorService service= Executors.newFixedThreadPool(100);
//        factory.setTaskExecutor(service);
//        factory.setPrefetchCount(5);

        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
