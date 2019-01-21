package com.guiji.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TunnelListReqVO对象",description="获取短信通道列表请求对象")
public class TunnelListReqVO
{
	@ApiModelProperty(value="每页条数")
	private int pageSize;
	@ApiModelProperty(value="每页条数")
	private int pageNum;
}
