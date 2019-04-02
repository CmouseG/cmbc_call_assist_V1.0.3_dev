package com.guiji.notice.entity;

import com.guiji.notice.enm.NoticeType;
import com.guiji.wechat.vo.SendMsgReqVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

@Data
public class MessageSend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID，注意用户的业务必须传递用户id")
    private Long userId;
    //userId字段如果传递，将忽略orgCode字段
    @ApiModelProperty(value = "企业orgCode,整个组织的消息传递orgCode")
    private String orgCode;

    @ApiModelProperty(value = "通知类型")
    private NoticeType noticeType;

    @ApiModelProperty(value = "站内信，消息内容")
    private String mailContent;

    @ApiModelProperty(value = "短信，消息内容")
    private String smsContent;

    @ApiModelProperty(value = "邮箱，消息内容")
    private String emailContent;

    @ApiModelProperty(value = "邮件主题")
    private String emailSubject;

    // 微信相关
    @ApiModelProperty(value = "模板ID")
    private String weixinTemplateId;

    @ApiModelProperty(value = "模板跳转链接（海外帐号没有跳转能力）")
    private String weixinUrl;

    @ApiModelProperty(value = "所需跳转到的小程序appid")
    private String weixinAppId;

    @ApiModelProperty(value = "所需跳转到小程序的具体页面路径")
    private String weixinPagePath;

    @ApiModelProperty(value = "模板数据")
    private HashMap<String, SendMsgReqVO.Item> weixinData;

}
