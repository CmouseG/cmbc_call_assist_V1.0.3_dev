package com.guiji.wechat.util.reply;

import com.guiji.wechat.util.enums.MsgTypeEnum;

/**
 * 文本回复消息对象
 */
public class TextReply extends BaseReply {

    /**
     * Content消息内容
     */
    private String Content;

    public TextReply() {
        super(MsgTypeEnum.TEXT.getKey());
    }

    public static TextReply build(){
        return new TextReply();
    }

    public String getContent() {
        return Content;
    }

    public TextReply setContent(String content) {
        Content = content;
        return this;
    }
}
