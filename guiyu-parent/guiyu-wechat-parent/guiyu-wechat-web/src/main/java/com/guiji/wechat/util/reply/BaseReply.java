package com.guiji.wechat.util.reply;

/**
 * 基础回复消息对象
 */
public class BaseReply {

    /**
     * ToUserName接收方帐号（收到的用户OpenID）
     */
    private String ToUserName;

    /**
     * FromUserName开发者微信号
     */
    private String FromUserName;

    /**
     * CreateTime消息创建时间 （整型）
     */
    private long CreateTime;

    /**
     * MsgType消息类型（text/image/location/link）
     */
    private String MsgType;

    public BaseReply() {
    }

    public BaseReply(String msgType) {
        CreateTime = System.currentTimeMillis();
        MsgType = msgType;
    }

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

    public BaseReply setToUserOpenId(String userOpenId) {
        ToUserName = userOpenId;
        return this;
    }

    public BaseReply setWeChatOpenId(String weChatOpenId) {
        FromUserName = weChatOpenId;
        return this;
    }
}
