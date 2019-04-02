package com.guiji.calloutserver.entity;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

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
     * 是否是开场白
     */
    private Boolean isPrologue;

    /**
     * 打断时间
     */
    private LocalTime disturbTime;

    /**
     * 开始播放时间
     */
    private Date startPlayTime;


    /**
     * 结束播放时间
     */
    private Date endPlayTime;

    /**
     * 预期结束播放时间
     */
    private Long expectEndPlayTime;

    /**
     * 当前通道是否正在播放媒体
     * @return
     */
    public boolean isInPlay(){
        return !Strings.isNullOrEmpty(mediaFileName);
    }
}
