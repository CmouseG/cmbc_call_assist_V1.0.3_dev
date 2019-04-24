package com.guiji.sms.controller.bean;

import java.util.Map;

import lombok.Data;

@Data
public class TunnelAddReq
{
	private String platformName; // 平台名称
	private String tunnelName; // 通道名称
	private Integer contentType; // 内容形式：1-短信内容；2-短信模版
	private Map<String,String> params; // 平台参数键值对
}
