package com.guiji.ai.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TaskListReqVO对象",description="获取任务列表请求条件")
public class TaskListReqVO
{
	@ApiModelProperty(value="业务Id")
	private String busId;
	@ApiModelProperty(value="模型名称")
	private String model;
	@ApiModelProperty(value="处理状态")
	private String status;
	@ApiModelProperty(value="起始时间")
	private Date startTime;
	@ApiModelProperty(value="结束时间")
	private Date endTime;
	@ApiModelProperty(value="每页条数")
	private Integer pageSize;
	@ApiModelProperty(value="页码")
	private Integer pageNum;
	
}
