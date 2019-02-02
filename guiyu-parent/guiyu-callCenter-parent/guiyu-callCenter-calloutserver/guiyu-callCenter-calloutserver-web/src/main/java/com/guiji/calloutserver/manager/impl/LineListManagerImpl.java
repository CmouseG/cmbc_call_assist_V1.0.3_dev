package com.guiji.calloutserver.manager.impl;

import com.guiji.calloutserver.manager.LineListManager;
import com.guiji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LineListManagerImpl implements LineListManager {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void addLineList(String callId, List<Integer> lineList) {

        //放入redis后顺序反了，取得时候从右边取
        redisUtil.leftPushAll("callCenter-lineList-"+callId,lineList);
        //第一个已经在呼叫了，直接拿出来
        redisUtil.lrightPop("callCenter-lineList-"+callId);
    }

    @Override
    public Integer popNewLine(String callId) {
        return (Integer) redisUtil.lrightPop("callCenter-lineList-"+callId);
    }

    @Override
    public boolean isEnd(String callId) {
        List<Object> list = redisUtil.lGet("callCenter-lineList-"+callId,0,10);
        if(list==null || list.size()==0){  // 线路都已经打完了，，end
            return true;
        }else{//线路了没打完。
            return false;
        }
    }
}
