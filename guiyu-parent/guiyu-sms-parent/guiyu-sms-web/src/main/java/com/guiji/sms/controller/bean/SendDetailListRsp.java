package com.guiji.sms.controller.bean;

import java.util.List;

import com.guiji.sms.dao.entity.SmsSendDetail;

import lombok.Data;

@Data
public class SendDetailListRsp
{
	private List<SmsSendDetail> records;
	private Long totalNum;
}
