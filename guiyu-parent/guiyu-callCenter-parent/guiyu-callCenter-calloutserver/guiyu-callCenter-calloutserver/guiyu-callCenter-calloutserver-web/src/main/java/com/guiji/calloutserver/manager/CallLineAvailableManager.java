package com.guiji.calloutserver.manager;


import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 对于线路是否可用的管理
 */
public interface CallLineAvailableManager {

    /**
     * 线路是可用的，不用再打下面的线路了
     * @param callId
     */
    void lineAreAvailable(String callId);

    /**
     * 线路是否可用
     * @param callId
     * @return
     */
    boolean isAvailable(String callId);

    /**
     * 电话已经接听
     * @param callId
     */
    @Async
    void channelAlreadyAnswer(String callId);

    /**
     * 查询电话是否接听
     * @param callId
     * @return
     */
    boolean isChannelAnswer(String callId);
}
