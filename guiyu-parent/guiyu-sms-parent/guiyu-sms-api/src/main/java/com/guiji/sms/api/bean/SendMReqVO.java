package com.guiji.sms.api.bean;

import lombok.Data;

@Data
public class SendMReqVO
{
	private Long userId; //用户id
	private String orgCode; //组织代码
	private String phone; // 手机号码
	private String intentionTag; //意向标签
	private String templateId; // 话术模版id
}
