package com.guiji.component.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/** 
* @ClassName: ThreadAsyncConfig 
* @Description: 异步多线程配置
* @author: weiyunbo
* @date 2018年8月16日 上午9:37:02 
* @version V1.0  
*/
@Configuration
@EnableAsync
public class ThreadAsyncConfig implements AsyncConfigurer{
		
	@Bean
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
	    //设置最小线程数
	    threadPool.setCorePoolSize(5);
	    //设置最大线程数
	    threadPool.setMaxPoolSize(20);
	    //等待队列
	    threadPool.setQueueCapacity(30);
	    //等待任务在关机时完成--表明等待所有线程执行完
	    threadPool.setWaitForTasksToCompleteOnShutdown(true);
	    // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
	    threadPool.setAwaitTerminationSeconds(60);
	    //  线程名称前缀
	    threadPool.setThreadNamePrefix("MyAsync-");
	    // rejection-policy：当pool已经达到max size的时候，如何处理新任务  
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行  
	    threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	    // 初始化线程
	    threadPool.initialize();
	    return threadPool;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}

}
