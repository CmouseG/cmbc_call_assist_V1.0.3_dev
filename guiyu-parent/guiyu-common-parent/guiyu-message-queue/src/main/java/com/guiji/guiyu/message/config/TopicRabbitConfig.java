package com.guiji.guiyu.message.config;
 
import com.rabbitmq.client.BuiltinExchangeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
public class TopicRabbitConfig {
     /**
     * exchange queue 多对多关系 一个交换下可以绑定多个队列 一个队列可以被多个交换机绑定
     */
    @Autowired
    ConnectionFactory connectionFactory;

    @Value("${rabbit.general.topic.exchange:}")
    private String topicExchangeStr;


    @Value("${rabbit.general.topic.queue:}")
    private String topicQueueStr;


    //主题关键字
    @Value("${rabbit.general.topic.routingKey:}")
    private String routingKeyStr;
    @Bean
    public boolean bindingTopicExchange() throws AmqpException,IOException {
        if(StringUtils.isNotEmpty(topicExchangeStr) && StringUtils.isNotEmpty(topicQueueStr) && StringUtils.isNotEmpty(routingKeyStr)){
            String[] topicExchanges =topicExchangeStr.split("\\|");
            String[] topicQueues =topicQueueStr.split("\\|");
            String[] routingKeys =routingKeyStr.split("\\|");
            for(int i=0;i<topicExchanges.length;i++){
                //声明交换机
                connectionFactory.createConnection().createChannel(false).exchangeDeclare(topicExchanges[i], BuiltinExchangeType.TOPIC,true);
                String[] subTopicQueues =topicQueues[i].split(",");
                String[] subRoutingKeys =routingKeys[i].split(",");
                for(int j=0;j<subTopicQueues.length;j++){
                    //创建队列
                    connectionFactory.createConnection().createChannel(false).queueDeclare(subTopicQueues[j], true, false, false, null);
                    //绑定交换机与队列
                    connectionFactory.createConnection().createChannel(false).queueBind(subTopicQueues[j],topicExchanges[i],subRoutingKeys[j]);
                }
            }
        }
        return true;
    }
}
