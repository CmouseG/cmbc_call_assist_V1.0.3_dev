package com.guiji.ai.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="AcceptTaskReqVO",description="累计接受任务请求对象")
public class AcceptTaskReqVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="起始时间")
	private Date startTime;
	@ApiModelProperty(value="结束时间")
	private Date endTime;
	@ApiModelProperty(value="维度（天，月）", required=true)
	private String dimension;
}
