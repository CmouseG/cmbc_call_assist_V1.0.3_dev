package com.guiji.calloutserver.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.guiji.calloutserver.entity.Channel;
import com.guiji.calloutserver.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
                .maximumSize(100)
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
    public void updateMediaLock(String uuid, boolean isLock, String wavFile){
        Channel channel = findByUuid(uuid);
        if(channel == null){
            channel = new Channel();
            channel.setChannelId(uuid);
        }

        channel.setIsMediaLock(isLock);
        channel.setMediaFileName(wavFile);
        save(channel);
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

    @Override
    public void delete(String channelId) {
        caches.invalidate(channelId);
    }

    @Override
    public boolean isMediaLock(String uuid) {
        Channel channel = findByUuid(uuid);
        return channel!=null && channel.getIsMediaLock();
    }
}