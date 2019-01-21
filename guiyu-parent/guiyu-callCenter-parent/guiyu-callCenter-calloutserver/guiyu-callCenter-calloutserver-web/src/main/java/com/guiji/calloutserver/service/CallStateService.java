package com.guiji.calloutserver.service;

import org.springframework.scheduling.annotation.Async;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/25 0025 14:59
 * @Description:
 */
public interface CallStateService {

    @Async
    void updateCallState();
}
