package com.guiji.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.guiji.entity.EUserType;
import com.guiji.entity.ECallDirection;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/24 17:05
 * @Project：guiyu-parent
 * @Description:
 */
@Component
public class CallCache {
    private Cache<String, CallInfo> directionCache;

    @PostConstruct
    public void init(){
        directionCache = CacheBuilder.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .build();
    }

    public void put(String channelUuid, String seqId, ECallDirection callDirection, EUserType userType){
        CallInfo item = new CallInfo();
        item.setSeqId(seqId);
        item.setCallDirection(callDirection);
        item.setUserType(userType);

        directionCache.put(channelUuid, item);
    }

    public CallInfo getCallInfo(String channelUuid){
        return directionCache.getIfPresent(channelUuid);
    }

    public void invalidate(String uuid) {
        directionCache.invalidate(uuid);
    }

    @Data
    public static class CallInfo {
        private ECallDirection callDirection;
        private String seqId;
        private EUserType userType;
    }
}
