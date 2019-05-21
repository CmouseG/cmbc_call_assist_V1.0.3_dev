package com.guiji.sms.controller.bean;

import lombok.Data;

@Data
public class TunnelSendTestReq
{
	private String phone;
	private String content;
	private String platformName;
	private String platformConfig;
	private String tunnelName;
	private Integer userId;
	private String orgCode;
}
