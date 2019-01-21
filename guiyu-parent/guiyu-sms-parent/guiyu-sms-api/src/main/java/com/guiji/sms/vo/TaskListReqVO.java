package com.guiji.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TaskListReqVO对象",description="获取短信任务列表请求对象")
public class TaskListReqVO
{
	@ApiModelProperty(value="每页条数")
	private int pageSize;
	@ApiModelProperty(value="页码")
	private int pageNum;
	@ApiModelProperty(value="任务状态")
	private Integer status; 
	@ApiModelProperty(value="任务名称")
	private String taskName; 
	@ApiModelProperty(value="发送时间")
	private String sendDate; 
}
