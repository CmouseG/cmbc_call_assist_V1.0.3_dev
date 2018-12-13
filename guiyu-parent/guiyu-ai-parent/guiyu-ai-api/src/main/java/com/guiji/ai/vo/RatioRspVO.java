package com.guiji.ai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="RatioRspVO对象",description="获取比率返回对象")
public class RatioRspVO
{
	@ApiModelProperty(value="成功率")
	private String successRatio;
	@ApiModelProperty(value="失败率")
	private String failRatio;
}
