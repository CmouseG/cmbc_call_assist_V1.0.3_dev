package com.guiji.sms.vo;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data 
@ApiModel(value="TunnelReqVO对象",description="新增短信通道请求对象")
public class TunnelReqVO
{
	@ApiModelProperty(value="平台名称")
	private String platformName;
	@ApiModelProperty(value="通道名称")
	private String tunnelName; 
	@ApiModelProperty(value="平台参数键值对")
	private Map<String,String> params;
}
