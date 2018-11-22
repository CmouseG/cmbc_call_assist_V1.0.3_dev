package com.guiji.calloutserver.entity;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/7 11:30
 * @Project：guiyu-parent
 * @Description: 用于存储FreeSWITCH通道的媒体相关信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Channel {
    /**
     * FreeSWITCH通道UUID
     */
    private String channelId;

    /**
     * 通道播放的媒体文件名称
     */
    private String mediaFileName;

    /**
     * 通道播放的媒体文件时长
     */
    private Double mediaFileDuration;

    /**
     * 锁定通道媒体, 在锁定期间，不允许播放其他媒体
     */
    private Boolean isMediaLock;

    /**
     * 打断时间
     */
    private LocalTime disturbTime;

    /**
     * 当前通道是否正在播放媒体
     * @return
     */
    public boolean isInPlay(){
        return !Strings.isNullOrEmpty(mediaFileName);
    }
}
