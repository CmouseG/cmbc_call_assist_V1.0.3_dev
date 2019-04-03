package com.guiji.calloutserver.boot;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/1 19:32
 * @Project：guiyu-parent
 * @Description:
 */
@Configuration
public class EventBusConfig {
    @Bean
    public AsyncEventBus asyncEventBus(){
        return new AsyncEventBus(Executors.newCachedThreadPool());
    }
}
