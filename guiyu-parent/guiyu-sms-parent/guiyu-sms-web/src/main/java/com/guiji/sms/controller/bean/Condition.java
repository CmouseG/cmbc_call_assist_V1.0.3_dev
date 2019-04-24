package com.guiji.sms.controller.bean;

import lombok.Data;

@Data
public class Condition
{
	private int pageSize;
	private int pageNum;
	private String orgName; // 组织名称
	private String taskName; // 任务名称
	private Integer sendStatus; // 发送状态：0-发送失败；1-发送成功 （发送详情页面）
	private Integer taskStatus; // 任务状态：0-未发送；1-发送中；2-已发送；3-发送失败 （群发任务页面）
	private String startDate;
	private String endDate;
}
