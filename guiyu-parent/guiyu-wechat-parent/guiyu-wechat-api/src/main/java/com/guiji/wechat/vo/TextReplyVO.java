package com.guiji.wechat.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "TextReplyVO对象", description = "文本回复消息对象")
public class TextReplyVO extends BaseReplyVO implements Serializable {

    private static final long serialVersionUID = -2720829569483555059L;

    // 消息内容
    @ApiModelProperty(value = "Content消息内容")
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
