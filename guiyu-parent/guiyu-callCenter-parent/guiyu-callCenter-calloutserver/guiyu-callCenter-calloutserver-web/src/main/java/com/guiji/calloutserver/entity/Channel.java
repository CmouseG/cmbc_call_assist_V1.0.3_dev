package com.guiji.calloutserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
