package com.guiji.dispatch.batchimport.config;

import com.guiji.component.threadpool.VisiableThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * @version V1.0
 * @ClassName: BatchImportThreadAsyncConfig
 * @Description: 异步多线程配置
 */
@Configuration
@EnableAsync
public class BatchImportThreadAsyncConfig
{
    @Bean(name = "asyncBatchImportExecutor")
    public Executor asyncBatchImportExecutor() {
        return new VisiableThreadPoolTaskExecutor("async-batch-import-", 5, 10, 100000, 60);
    }

    //    @Bean(name = "asyncBatchImportSaveDBExecutor")
    //    public Executor asyncBatchImportSaveDBExecutor()
    //    {
    //        return new VisiableThreadPoolTaskExecutor("async-batch-import-savedb-", 40, 100, 200000, 60);
    //    }


}
