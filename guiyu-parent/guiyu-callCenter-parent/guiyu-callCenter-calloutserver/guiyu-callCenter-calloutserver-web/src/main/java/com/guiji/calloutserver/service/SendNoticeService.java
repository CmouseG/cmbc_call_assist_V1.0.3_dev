package com.guiji.calloutserver.service;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import org.springframework.scheduling.annotation.Async;

public interface SendNoticeService {

    @Async
    void sendNotice(CallOutPlan callOutPlan);

    void dealIntentWNotice(String intent, int userId, int linId);

    void sendFNotice(long userId);

    void sendWNotice(long userId);
}
