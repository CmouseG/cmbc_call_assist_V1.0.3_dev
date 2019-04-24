package com.guiji.sms.controller.bean;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class TaskReq
{
	private Integer id;
	private Integer sendType; // 发送方式：1-立即发送；2-定时发送
	private String taskName; // 任务名称
	private String tunnelName; // 通道名称
	private Integer contentType; // 内容形式：1-短信内容；2-短信模版
	private String smsContent; // 短信内容
	private String sendTime; // 发送时间 
	private MultipartFile file; // 发送名单文件
}
