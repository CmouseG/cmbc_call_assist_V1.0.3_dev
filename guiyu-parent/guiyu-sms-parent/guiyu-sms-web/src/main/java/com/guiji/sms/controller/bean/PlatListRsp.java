package com.guiji.sms.controller.bean;

import java.util.List;

import com.guiji.sms.dao.entity.SmsPlatform;

import lombok.Data;

@Data
public class PlatListRsp
{
	private List<SmsPlatform> records;
	private Long totalNum;
}
