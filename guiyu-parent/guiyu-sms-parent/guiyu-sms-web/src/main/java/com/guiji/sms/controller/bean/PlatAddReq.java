package com.guiji.sms.controller.bean;

import lombok.Data;

@Data
public class PlatAddReq
{
	private String platformName; // 平台名称
	private String identification; // 内部标识
	private String params; // 参数
	private Integer contentType; // 内容形式：1-短信内容；2-短信模版
}
