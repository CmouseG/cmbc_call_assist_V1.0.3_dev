package com.guiji.sms.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="PlatformRspVO对象",description="获取短信平台名称列表及参数返回对象")
public class PlatformRspVO
{
	@ApiModelProperty(value="平台名称")
	private String platformName;
	@ApiModelProperty(value="平台参数")
	private List<String> params;
}
