package com.guiji.calloutserver.eventbus.event;

import com.guiji.calloutserver.util.CommonUtil;
import lombok.Data;

/**
 * ASR识别事件
 */
@Data
public class AsrBaseEvent {
    private String uuid;
    private String asrText;
    private String channel;
    private String timestamp;
    private String filePath;
    private String fileName;
    private String answered;
    private String asrStartTime;
    private String asrEndTime;
    private Long asrDuration;

    /**
     * 是否为系统自己产生的事件，用于没有asr识别消息的时候，将会话进行下去
     */
    private boolean isGenerated;

    public String toString(){
        return CommonUtil.beanToJson(this);
    }
}
