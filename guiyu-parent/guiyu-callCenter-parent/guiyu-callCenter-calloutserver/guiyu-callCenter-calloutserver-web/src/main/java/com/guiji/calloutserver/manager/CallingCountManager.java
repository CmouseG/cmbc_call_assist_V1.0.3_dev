package com.guiji.calloutserver.manager;

import org.springframework.scheduling.annotation.Async;

/**
 * 对freeswitch当前通话数的管理
 */
public interface CallingCountManager {


    /**
     * 增加一个通话数
     */
    @Async
    void addOneCall();

    /**
     * 减少一个通话数
     */
    @Async
    void removeOneCall();

    int getCallCount();
}
