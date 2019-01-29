package com.guiji.botsentence.api.entity;

import lombok.Data;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/9 15:08
 * @Project：guiji-parent
 * @Description:
 */
@Data
public class SelfTestVO {
    private String uuid;
    private String sentence;
    private String tempId;
    private String channel;
    private String processId;
}
