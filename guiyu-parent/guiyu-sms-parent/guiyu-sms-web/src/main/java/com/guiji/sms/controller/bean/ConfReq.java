package com.guiji.sms.controller.bean;

import lombok.Data;

@Data
public class ConfReq
{
	private Integer id;
	private String tunnelName; // 通道名称
	private String templateId; // 话术模版id
	private String templateName; // 话术模版名称
	private String intentionTag; // 意向标签
	private Integer contentType; // 内容形式：1-短信内容；2-短信模版
	private String smsContent; // 短信内容
}
