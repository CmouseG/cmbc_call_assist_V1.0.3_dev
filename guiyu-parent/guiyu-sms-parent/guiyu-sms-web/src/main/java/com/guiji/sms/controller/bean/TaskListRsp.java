package com.guiji.sms.controller.bean;

import java.util.List;

import com.guiji.sms.dao.entity.SmsTask;

import lombok.Data;

@Data
public class TaskListRsp
{
	private List<SmsTask> records;
	private Long totalNum;
}
