package com.guiji.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="PlatformReqVO对象",description="新增短信平台请求对象")
public class PlatformReqVO
{
	@ApiModelProperty(value="平台名称")
	private String platformName;
	@ApiModelProperty(value="内部标识")
	private String identification;
	@ApiModelProperty(value="参数")
	private String params;
}
