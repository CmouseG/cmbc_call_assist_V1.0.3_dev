package com.guiji.ai.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TaskRspVO",description="累计任务返回对象")
public class TaskRspVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="查询总数")
	private Integer totalNum;
	@ApiModelProperty(value="任务数列表")
	private List<TaskNumVO> taskNumsList;
	
}
