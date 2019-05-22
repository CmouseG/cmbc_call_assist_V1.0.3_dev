package com.guiji.delay;

import lombok.Data;

/**
 * 延迟队列信息
 */
@Data
public class DelayMessage {

    /**
     * 当前是第几次，从0开始
     */
    private Integer currNum;

    /**
     * http的报文
     */
    private String body;

    /**
     * url地址
     */
    private String notifyUrl;

}
