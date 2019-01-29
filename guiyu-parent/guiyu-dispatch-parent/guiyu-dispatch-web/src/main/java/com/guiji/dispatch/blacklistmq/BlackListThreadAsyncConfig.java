package com.guiji.dispatch.blacklistmq;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.guiji.component.threadpool.VisiableThreadPoolTaskExecutor;

/**
 * @version V1.0
 * @ClassName: BlackListThreadAsyncConfig
 * @Description: 异步多线程配置
 */
@Configuration
@EnableAsync
public class BlackListThreadAsyncConfig {
    @Bean(name = "asyncProcessBlackList")
    public Executor asyncProcessMsgReadExecutor() {
        return new VisiableThreadPoolTaskExecutor("async-blacklist-import-", 5, 10, 10000, 60);
    }
}
