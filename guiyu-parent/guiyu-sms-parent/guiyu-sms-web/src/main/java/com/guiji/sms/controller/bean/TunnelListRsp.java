package com.guiji.sms.controller.bean;

import java.util.List;

import com.guiji.sms.dao.entity.SmsTunnel;

import lombok.Data;

@Data
public class TunnelListRsp
{
	private List<SmsTunnel> records;
	private Long totalNum;
}
