package com.guiji.calloutserver.service;

import org.springframework.scheduling.annotation.Async;

public interface SendNoticeService {

    @Async
    void sendNotice(Integer userId, String phone, String intent);
}
