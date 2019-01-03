package com.guiji.ai.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TaskReqVO",description="累计任务请求对象")
public class TaskReqVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="起始时间")
	private String startTime;
	@ApiModelProperty(value="结束时间")
	private String endTime;
	@ApiModelProperty(value="维度（天，月）", required=true)
	private String dimension;
	@ApiModelProperty(value="返回标识，区分返回结果,0-累计接受任务数;1-累计完成任务数", required=true)
	private String returnFlag;
}
