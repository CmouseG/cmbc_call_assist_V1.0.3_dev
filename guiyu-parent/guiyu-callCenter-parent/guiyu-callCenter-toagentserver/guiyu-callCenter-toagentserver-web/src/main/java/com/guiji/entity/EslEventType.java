package com.guiji.entity;

public enum EslEventType {
    /**
     * ASR识别事件
     */
    EV_ALIASR,

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

    CALLCENTER_INFO,

    VERTO_DISCONNECT,

    VERTO_LOGIN;

    public static EslEventType getByValue(String value){
        EslEventType event = null;
        try{
            event = EslEventType.valueOf(value);
        }catch (Exception ex){}

        if(event == null){
            if(value.equals("callcenter::info")){
                event = EslEventType.CALLCENTER_INFO;
            }else if(value.equals("verto::client_disconnect")){
                event = EslEventType.VERTO_DISCONNECT;
            }else if(value.equals("verto::login")){
                event = EslEventType.VERTO_LOGIN;
            }
        }

        return event;
    }
}
