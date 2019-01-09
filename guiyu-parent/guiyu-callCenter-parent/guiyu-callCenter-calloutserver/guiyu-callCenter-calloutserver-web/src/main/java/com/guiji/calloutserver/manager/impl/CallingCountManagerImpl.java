package com.guiji.calloutserver.manager.impl;

import com.guiji.calloutserver.manager.CallingCountManager;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CallingCountManagerImpl implements CallingCountManager {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    EurekaManager eurekaManager;

    @Override
    @Async
    public void addOneCall(){
        if(redisUtil.get("callCenter-callCount-"+eurekaManager.getInstanceId())!=null){
            redisUtil.incr("callCenter-callCount-"+eurekaManager.getInstanceId(),1);
        }else{
            redisUtil.set("callCenter-callCount-"+eurekaManager.getInstanceId(),0);
        }
    }

    @Override
    @Async
    public void removeOneCall(){
        if(redisUtil.get("callCenter-callCount-"+eurekaManager.getInstanceId())!=null){
            redisUtil.decr("callCenter-callCount-"+eurekaManager.getInstanceId(),1);
        }else{
            redisUtil.set("callCenter-callCount-"+eurekaManager.getInstanceId(),0);
        }
    }

}
