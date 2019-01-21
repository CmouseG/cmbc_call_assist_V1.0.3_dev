package com.guiji.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="PlatformListReqVO对象",description="获取短信平台列表请求对象")
public class PlatformListReqVO
{
	@ApiModelProperty(value="每页条数")
	private int pageSize;
	@ApiModelProperty(value="页码")
	private int pageNum;
}
