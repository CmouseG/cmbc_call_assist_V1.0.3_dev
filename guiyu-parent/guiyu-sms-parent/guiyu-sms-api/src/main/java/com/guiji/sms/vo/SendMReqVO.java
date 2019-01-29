package com.guiji.sms.vo;

import lombok.Data;

@Data
public class SendMReqVO
{
	private String intentionTag; //意向标签
	private String phone; // 手机号码
	private Integer userId; //用户 
	private String orgCode; //组织代码
	private String templateId; // 话术模版id
}
