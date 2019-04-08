package com.guiji.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TaskReq
{
	private String taskName;
	private Integer sendType;
	private List<String> phoneList;
	private String tunnelName;
	private Integer smsTemplateId;
	private String smsContent;
	private Date sendTime;
	private Long userId;
	private String companyName;
	private String taskId;
	
	public TaskReq()
	{}
	
	public TaskReq(String taskName,Integer sendType,List<String> phoneList,String tunnelName,Integer smsTemplateId,String smsContent)
	{
		this.taskName = taskName;
		this.sendType = sendType;
		this.phoneList = phoneList;
		this.tunnelName = tunnelName;
		this.smsTemplateId = smsTemplateId;
		this.smsContent = smsContent;
	}
}
