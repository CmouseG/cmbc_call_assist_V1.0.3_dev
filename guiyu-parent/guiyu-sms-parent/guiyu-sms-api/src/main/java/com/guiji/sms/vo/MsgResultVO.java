package com.guiji.sms.vo;

import java.util.Date;

import lombok.Data;

@Data
public class MsgResultVO
{
	private String planuuid;
	private String phone;
	private String smsContent;
	private String sendStatus;
	private String companyName;
	private String tunnelName;
	private Date sendTime;
}
