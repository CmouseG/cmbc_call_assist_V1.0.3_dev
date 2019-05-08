package com.guiji.callcenter.fsmanager.config;

import com.rabbitmq.client.BuiltinExchangeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * 创建exchange
 */
@Configuration
public class LineRabbitConfig {

    @Autowired
    ConnectionFactory connectionFactory;

    @Value("${rabbit.general.fanout.exchange:}")
    private String fanoutExchange;

    @Bean
    public boolean bindingFanoutExchange() throws AmqpException, IOException {
        if (StringUtils.isNotEmpty(fanoutExchange)) {
            connectionFactory.createConnection().createChannel(false).exchangeDeclare(fanoutExchange, BuiltinExchangeType.FANOUT,true);
        }
        return true;
    }
}

