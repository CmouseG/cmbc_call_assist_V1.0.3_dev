package com.guiji.calloutserver.manager.impl;

import com.guiji.calloutserver.manager.CallLineAvailableManager;
import com.guiji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallLineAvailableManagerImpl implements CallLineAvailableManager {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Async
    public void lineAreAvailable(String callId) {

        //暂存2个小时  2*60*60
        redisUtil.set("callCenter-lineAvailable-"+callId,true,7200);

    }


    @Override
    public boolean isAvailable(String callId) {

        if(redisUtil.get("callCenter-lineAvailable-"+callId)==null){//线路不可用
            return false;
        }else{  //线路可用
            return true;
        }
    }

    @Override
    @Async
    public void channelAlreadyAnswer(String callId) {

        //暂存1个小时  1*60*60
        redisUtil.set("callCenter-channelAnswer-"+callId,true,3600);

    }


    @Override
    public boolean isChannelAnswer(String callId) {

        if(redisUtil.get("callCenter-channelAnswer-"+callId)==null){//没有接
            return false;
        }else{
            return true;
        }
    }
}
