package com.guiji.calloutserver.service;

import com.guiji.calloutserver.entity.Channel;

import java.time.LocalTime;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/7 11:30
 * @Project：guiyu-parent
 * @Description:
 */
public interface ChannelService {
    void save(Channel channel);
    Channel findByUuid(String channelId);
    void delete(String channelId);

    void updateMediaLock(String channelId, boolean isLock);

    void updateMediaLock(String uuid, Boolean isLock, Boolean isPrologue, String wavFile, LocalTime disturbTime);

    boolean isMediaLock(String uuid);

}
