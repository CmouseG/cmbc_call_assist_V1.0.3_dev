package com.guiji.dispatch.batchimport.config;

import com.guiji.component.threadpool.VisiableThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class BatchExportPlanAsyncConfig {

    @Bean(name = "asyncImportPlanExecutor")
    public Executor asyncImportPlanExecutor() {
        return new VisiableThreadPoolTaskExecutor("async-succPhone-consumer-", 5, 10, 100000, 60);
    }
}
