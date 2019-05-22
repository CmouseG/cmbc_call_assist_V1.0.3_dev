package com.guiji.dispatch.config;

import com.guiji.component.threadpool.VisiableThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * @Classname NotifyThreadConfig
 * @Description TODO
 * @Date 2019/5/23 19:31
 * @Created by qinghua
 */
@Configuration
@EnableAsync
public class NotifyThreadConfig {

    @Bean(name = "asyncThirdApiNotify")
    public Executor asyncProcessMsgReadExecutor() {
        return new VisiableThreadPoolTaskExecutor("async-notify-thirdApi-", 5, 10, 10000, 60);
    }

}