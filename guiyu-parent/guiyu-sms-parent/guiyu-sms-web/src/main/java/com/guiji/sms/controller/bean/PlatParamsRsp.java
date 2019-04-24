package com.guiji.sms.controller.bean;

import java.util.List;

import lombok.Data;

@Data
public class PlatParamsRsp
{
	private String platformName; // 平台名称
	private Integer contentType; // 内容形式：1-短信内容；2-短信模版
	private List<String> params; // 平台参数
}
