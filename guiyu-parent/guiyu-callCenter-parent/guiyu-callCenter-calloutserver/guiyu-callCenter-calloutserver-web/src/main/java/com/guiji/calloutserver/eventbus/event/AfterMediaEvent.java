package com.guiji.calloutserver.eventbus.event;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 媒体文件播放结束事件
 */
@Data
@AllArgsConstructor
public class AfterMediaEvent {
    private String uuid;
}
