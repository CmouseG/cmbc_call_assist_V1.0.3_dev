package com.guiji.listener;

import lombok.Data;

/**
 * 用于接收相关的异步消息
 */
@Data
public class MqMessage {

    /**
     * 异步消息的userId
     */
    private Long userId;

    /**
     * 回调url
     */
    private String notifyUrl;

    /**
     * 未加密报文(jsonstring)
     */
    private String body;

}
