package com.guiji.calloutserver.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.guiji.calloutserver.entity.Channel;
import com.guiji.calloutserver.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/7 11:35
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class ChannelServiceImpl implements ChannelService {
    private Cache<String, Channel> caches;

    @PostConstruct
    public void init(){
        caches = CacheBuilder.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public void save(Channel channel) {
        caches.put(channel.getChannelId(), channel);
    }

    @Override
    public Channel findByUuid(String channelId) {
        return caches.getIfPresent(channelId);
    }

    @Override
    public void updateMediaLock(String uuid, boolean isLock){
        Channel channel = findByUuid(uuid);
        if(channel == null){
            channel = new Channel();
            channel.setChannelId(uuid);
        }

        channel.setIsMediaLock(isLock);
        save(channel);
    }

    @Transactional
    public void updateMediaLock(String uuid, Boolean isLock,Boolean isPrologue, String wavFile, LocalTime disturbTime){
        Channel callMedia = findByUuid(uuid);
        if(callMedia == null){
            callMedia = new Channel();
            callMedia.setChannelId(uuid);
        }

        callMedia.setIsMediaLock(isLock);
        callMedia.setIsPrologue(isPrologue);
        callMedia.setMediaFileName(wavFile);
        callMedia.setDisturbTime(disturbTime);
        callMedia.setEndPlayTime(new Date());
        save(callMedia);
    }

    @Override
    public void delete(String channelId) {
        caches.invalidate(channelId);
    }

    @Override
    public boolean isMediaLock(String uuid) {
        Channel channel = findByUuid(uuid);
        //开场白前面的用户说话不处理，sellbot提示结束之后用户说话不处理。故将channel!=null 改为channel==null
        return channel==null || channel.getIsMediaLock();
    }
}
