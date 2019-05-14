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
    private RedisUtil redisUtil;
    @Autowired
    EurekaManager eurekaManager;

    @Override
    @Async
    public void addOneCall(){
//        String key = "callCenter-callCount-"+eurekaManager.getInstanceId();
//        if(redisUtil.get(key)!=null){
//            redisUtil.incr(key,1);
//        }else{
//            redisUtil.set(key,0);
//        }
    }

    @Override
    @Async
    public void removeOneCall(){
//        String key = "callCenter-callCount-"+eurekaManager.getInstanceId();
//        Object value = redisUtil.get(key);
//        if(value!=null && (int)value>0){
//            redisUtil.decr(key,1);
//        }else{
//            redisUtil.set(key,0);
//        }
    }

    @Override
    public int getCallCount(){
//        String key = "callCenter-callCount-"+eurekaManager.getInstanceId();
//        Object object = redisUtil.get(key);
//        if(object!=null){
//            return (int) object;
//        }
        return 0;
    }

}
