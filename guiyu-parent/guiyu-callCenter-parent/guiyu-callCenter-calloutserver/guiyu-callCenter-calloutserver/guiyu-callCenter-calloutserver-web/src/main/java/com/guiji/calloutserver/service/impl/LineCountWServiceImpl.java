package com.guiji.calloutserver.service.impl;

import com.guiji.calloutserver.service.LineCountWService;
import com.guiji.calloutserver.service.SendNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LineCountWServiceImpl implements LineCountWService {

    @Autowired
    SendNoticeService sendNoticeService;

    @Override
    @Async
    public void addWCount(Integer lineId, String orgCode, long userId){

        sendNoticeService.dealIntentWNotice("W", (int) userId,lineId);

    }


}
