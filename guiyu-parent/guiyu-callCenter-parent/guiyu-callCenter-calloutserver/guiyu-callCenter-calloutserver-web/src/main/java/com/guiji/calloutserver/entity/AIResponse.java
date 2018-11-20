package com.guiji.calloutserver.entity;

import com.guiji.calloutserver.enm.EAIResponseType;
import lombok.Builder;
import lombok.Data;

@Data
//@Builder
public class AIResponse {
    private boolean result;
    private String msg;

    //通话uuid，也是FreeSWITCH的通道uuid
    private String callId;

    //机器人标识该次通话的id
    private String aiId;

    //待播放的录音文件时长
    private String wavFile;

    //文件时长
    private Double wavDuration;

    //sellbot应答文本
    private String responseTxt;

    //意向标签
    private String accurateIntent;

    //原因
    private String reason;

    //应答类型
    private EAIResponseType aiResponseType;

    //是否匹配到ai的关键词
    private boolean isMatched;

}
