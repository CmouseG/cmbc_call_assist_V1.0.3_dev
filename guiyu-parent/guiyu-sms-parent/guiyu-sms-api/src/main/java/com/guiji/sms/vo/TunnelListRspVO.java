package com.guiji.sms.vo;

import java.util.List;

import com.guiji.sms.dao.entity.SmsTunnel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TunnelListRspVO对象",description="获取短信通道列表返回对象")
public class TunnelListRspVO
{
	@ApiModelProperty(value="总条数")
	private int totalCount;
	@ApiModelProperty(value="通道列表")
	private List<SmsTunnel> smsTunnelList;
}
