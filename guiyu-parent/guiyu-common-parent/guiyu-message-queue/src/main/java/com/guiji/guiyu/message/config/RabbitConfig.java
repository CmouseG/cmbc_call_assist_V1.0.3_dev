package com.guiji.guiyu.message.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
class RabbitConfig{

    @Value("${rabbit.general.direct.queue:}")
    private String directQueue;

    @Autowired
    ConnectionFactory connectionFactory;

    /**
     * 动态生成队列
     * @return
     * @throws AmqpException
     * @throws IOException
     */
    @Bean
    public String[] mqMsgQueues() throws AmqpException,IOException {
        String[] queueNames = null;
        if(StringUtils.isNotEmpty(directQueue)){
            queueNames =directQueue.split("\\|");
            for(int i=0;i<queueNames.length;i++){
                connectionFactory.createConnection().createChannel(false).queueDeclare(queueNames[i], true, false, false, null);
            }
        }
        return queueNames;
    }
}