package com.guiji.calloutserver.service;

import org.springframework.scheduling.annotation.Async;

public interface LineCountWService {
    @Async
    void addWCount(Integer lineId, String orgCode, long userId);
}
