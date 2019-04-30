package com.guiji.calloutserver.enm;

public enum EslEventType {
    /**
     * ASR识别事件
     */
    EV_ALIASR,

    /**
     * ASR错误事件
     */
    EV_ALIASR_E,

    /**
     * 通道应答事件
     */
    CHANNEL_ANSWER,

    /**
     * 通道挂断事件
     */
    CHANNEL_HANGUP,

    CHANNEL_HANGUP_COMPLETE,

    /**
     * 通道振铃事件
     */
    CHANNEL_PROGRESS,
    CHANNEL_PROGRESS_MEDIA,

    CALLCENTER_INFO;

    public static EslEventType getByValue(String value){
        EslEventType event = null;
        try{
            event = EslEventType.valueOf(value);
        }catch (Exception ex){}

        if(event == null){
            if(value.equals("callcenter::info")){
                event = EslEventType.CALLCENTER_INFO;
            }
        }

        return event;
    }
}
