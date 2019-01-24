package com.guiji.entity;

public class SmsConstants 
{
	// 错误码
	public static final String Error_Request = "1000001"; // 请求失败
	
	// 发送方式
	public static final int HandSend = 0; // 0-手动发送
	public static final int TimeSend = 1; // 1-定时发送
	
	// 发送状态
	public static final int UnStart = 0; // 0-未开始
	public static final int Begining = 1; // 1-进行中
	public static final int end = 2; // 2-已结束
	
	// 未审核
	public static final int UnAuditing = 0; // 未审核
}
