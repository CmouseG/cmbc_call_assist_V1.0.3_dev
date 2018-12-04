package com.guiji.guiyu.message.config;
 
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FanoutRabbitConfig {
 
    @Bean
    public Queue PublishBotstenceSellbotMessage() {
        return new Queue("fanoutPublishBotstence.SELLBOT");
    }
 
    @Bean
    public Queue PublishBotstenceFreeswitchMessage() {
        return new Queue("fanoutPublishBotstence.FREESWITCH");
    }
 
    @Bean
    public Queue PublishBotstenceRobotMessage() {
        return new Queue("fanoutPublishBotstence.ROBOT");
    }
 
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutPublishBotstence");
    }
 
    @Bean
    Binding bindingExchangeA(Queue PublishBotstenceSellbotMessage,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(PublishBotstenceSellbotMessage).to(fanoutExchange);
    }
 
    @Bean
    Binding bindingExchangeB(Queue PublishBotstenceFreeswitchMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(PublishBotstenceFreeswitchMessage).to(fanoutExchange);
    }
 
    @Bean
    Binding bindingExchangeC(Queue PublishBotstenceRobotMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(PublishBotstenceRobotMessage).to(fanoutExchange);
    }
 
}
