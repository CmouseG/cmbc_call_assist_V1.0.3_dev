package com.guiji.calloutserver.manager.impl;

import com.guiji.calloutserver.manager.CallLineAvailableManager;
import com.guiji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallLineAvailableManagerImpl implements CallLineAvailableManager {

    @Autowired
    private RedisUtil redisUtil;

    @Override
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
}
