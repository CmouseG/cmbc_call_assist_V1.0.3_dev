package com.guiji.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="ConfigListReqVO对象",description="获取短信配置列表请求对象")
public class ConfigListReqVO
{
	@ApiModelProperty(value="每页条数")
	private int pageSize;
	@ApiModelProperty(value="页码")
	private int pageNum;
}
