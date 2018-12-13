package com.guiji.ai.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="RatioReqVO对象",description="获取比率请求对象")
public class RatioReqVO
{
	@ApiModelProperty(value="起始时间")
	private Date startTime;
	@ApiModelProperty(value="结束时间")
	private Date endTime;
}