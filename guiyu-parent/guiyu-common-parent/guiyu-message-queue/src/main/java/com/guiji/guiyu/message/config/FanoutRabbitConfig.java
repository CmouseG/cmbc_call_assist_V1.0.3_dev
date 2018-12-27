package com.guiji.guiyu.message.config;
 
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
public class FanoutRabbitConfig {

     /**
     * exchange queue 多对多关系 一个交换下可以绑定多个队列 一个队列可以被多个交换机绑定
     */
    @Autowired
    ConnectionFactory connectionFactory;

    @Value("${rabbit.general.fanout.exchange:}")
    private String fanoutExchange;


    @Value("${rabbit.general.fanout.queue:}")
    private String fanoutQueue;

    @Bean
    public boolean bindingFanoutExchange() throws AmqpException,IOException {
        if(StringUtils.isNotEmpty(fanoutExchange) && StringUtils.isNotEmpty(fanoutQueue)){
            String[] fanoutExchanges =fanoutExchange.split("\\|");
            String[] fanoutQueues =fanoutQueue.split("\\|");
            for(int i=0;i<fanoutExchanges.length;i++){
                // 创建交换机
                connectionFactory.createConnection().createChannel(false).exchangeDeclare(fanoutExchanges[i], BuiltinExchangeType.FANOUT,true);
                String[] subFanoutQueues =fanoutQueues[i].split(",");
                for(int j=0;j<subFanoutQueues.length;j++){
                    //创建队列
                    connectionFactory.createConnection().createChannel(false).queueDeclare(subFanoutQueues[j], true, false, false, null);
                    //绑定交换机与队列
                    connectionFactory.createConnection().createChannel(false).queueBind(subFanoutQueues[j],fanoutExchanges[i],"");
                }
            }
        }
        return true;
    }
}
