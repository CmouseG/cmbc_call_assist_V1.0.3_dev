package com.guiji.calloutserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * author:liyang
 * Date:2019/4/9 20:41
 * Description:
 */
@Slf4j
public class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    //手动处理捕获的异常
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        log.info("-------------》》》捕获线程异常信息");
        log.info("Exception message - " + throwable.getMessage());
        log.info("Method name - " + method.getName());
        for (Object param : obj) {
            log.info("Parameter value - " + param);
        }
    }
}
