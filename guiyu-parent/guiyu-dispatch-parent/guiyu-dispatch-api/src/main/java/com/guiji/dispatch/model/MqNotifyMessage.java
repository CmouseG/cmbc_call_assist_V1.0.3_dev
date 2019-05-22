package com.guiji.dispatch.model;

import lombok.Data;

/**
 * @Classname MqNotifyMessage
 * @Description TODO
 * @Date 2019/5/22 13:39
 * @Created by qinghua
 */
@Data
public class MqNotifyMessage {

    private Integer userId;

    private String notifyUrl;

    private String body;

}
