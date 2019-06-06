package com.guiji.calloutserver.manager.impl;

import com.guiji.calloutserver.manager.SimCallManager;
import com.guiji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:liyang
 * Date:2019/3/9 14:19
 * Description:
 */
@Service
public class SimCallManagerImpl implements SimCallManager {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void addSimCall(String callId, Boolean simCall) {

        if(simCall!=null && simCall){
            redisUtil.set("cout_simCall_"+callId,simCall,3600); //1个小时
        }

    }


    @Override
    public boolean isSimCall(String callId) {
        if(redisUtil.get("cout_simCall_"+callId)!=null){
            return true;
        }
        return false;
    }
}
