package com.guiji.sms.controller.bean;

import lombok.Data;

@Data
public class TunnelParamsRsp
{
	private String tunnelName; // 通道名称
	private Integer contentType; // 内容形式：1-短信内容；2-短信模版
}
