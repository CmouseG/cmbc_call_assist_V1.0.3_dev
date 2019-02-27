package com.guiji.wechat.dtos;

import com.guiji.wechat.util.enums.EventTypeEnum;
import com.guiji.wechat.util.enums.MsgTypeEnum;

import java.util.Date;

public class EventMsgReqDto {

    private final static String WECHAT_REGEX = "qrscene_";

    /**
     * 开发者微信号
     */
    private String toUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    private String fromUserName;

    /**
     * 消息创建时间
     */
    private Date createTime;

    /**
     * 消息类型
     */
    private MsgTypeEnum msgTypeEnum;

    /**
     * 事件类型
     */
    private EventTypeEnum eventTypeEnum;

    /**
     * 事件KEY值
     */
    private String eventKey;

    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    private String ticket;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public MsgTypeEnum getMsgTypeEnum() {
        return msgTypeEnum;
    }

    public void setMsgTypeEnum(MsgTypeEnum msgTypeEnum) {
        this.msgTypeEnum = msgTypeEnum;
    }

    public EventTypeEnum getEventTypeEnum() {
        return eventTypeEnum;
    }

    public void setEventTypeEnum(EventTypeEnum eventTypeEnum) {
        this.eventTypeEnum = eventTypeEnum;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getCallbackParameter(){
        if(eventKey.startsWith(WECHAT_REGEX)){
            return eventKey.split(WECHAT_REGEX)[1];
        }
        return eventKey;
    }
}
