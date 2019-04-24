package com.guiji.sms.controller.bean;

import java.util.List;

import com.guiji.sms.dao.entity.SmsConfig;

import lombok.Data;

@Data
public class ConfListRsp
{
	private List<SmsConfig> records;
	private Long totalNum;
}
