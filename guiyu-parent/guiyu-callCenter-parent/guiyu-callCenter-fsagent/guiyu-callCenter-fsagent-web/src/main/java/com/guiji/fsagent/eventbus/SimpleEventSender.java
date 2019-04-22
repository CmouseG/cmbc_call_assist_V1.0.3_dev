package com.guiji.fsagent.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Component
public class SimpleEventSender {

    private final AsyncEventBus eventBus;

    public SimpleEventSender(){
        eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
    }

    /**
     * 注册处理方法
     * @param obj
     */
    public void register(Object obj){
        eventBus.register(obj);
    }

    /**
     * 发送事件
     * @param obj
     */
    public void sendEvent(Object obj) {
        this.eventBus.post(obj);
    }
}
