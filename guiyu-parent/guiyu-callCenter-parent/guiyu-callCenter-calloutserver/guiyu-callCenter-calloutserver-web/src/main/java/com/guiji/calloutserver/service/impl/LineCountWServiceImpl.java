package com.guiji.calloutserver.service.impl;

import com.guiji.calloutserver.service.LineCountWService;
import com.guiji.calloutserver.service.SendNoticeService;
import com.guiji.utils.RedisUtil;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LineCountWServiceImpl implements LineCountWService {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    SendNoticeService sendNoticeService;

    @Override
    @Async
    public void addWCount(Integer lineId, String orgCode, long userId){
        String countWKey = "callCenter_W_count_lineId_"+lineId+"_orgCode_"+orgCode;
        Object countWValue = redisUtil.get(countWKey);
        if(countWValue!=null ){
            if((int) countWValue>=99){
                //此处有并发问题
                synchronized(LineCountWServiceImpl.class){

                    int newValue = (int) redisUtil.get(countWKey);
                    if(newValue>=99){
                        redisUtil.set(countWKey,0);
                        log.info("发起呼叫前，产生线路报错,linId[{}],count[{}],orgCode[{]]",lineId,countWValue,orgCode);
                        sendNoticeService.sendWNotice(userId);
                    }else{
                        redisUtil.incr(countWKey,1);
                    }
                }
            }else{
                redisUtil.incr(countWKey,1);
            }
        }else{
            redisUtil.set(countWKey,1);
        }
    }


}
