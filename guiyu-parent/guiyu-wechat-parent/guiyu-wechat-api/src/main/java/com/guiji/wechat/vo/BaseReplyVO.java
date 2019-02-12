package com.guiji.wechat.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BaseReplyVO对象", description = "基础回复消息对象")
public class BaseReplyVO {

    @ApiModelProperty(value = "ToUserName接收方帐号（收到的OpenID）")
    private String ToUserName;

    @ApiModelProperty(value = "FromUserName开发者微信号")
    private String FromUserName;

    @ApiModelProperty(value = "CreateTime消息创建时间 （整型）")
    private long CreateTime;

    @ApiModelProperty(value = "MsgType消息类型（text/image/location/link）")
    private String MsgType;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
