package com.guiji.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TaskDetailListReqVO对象",description="获取短信详情列表请求对象")
public class TaskDetailListReqVO
{
	@ApiModelProperty(value="每页条数")
	private int pageSize;
	@ApiModelProperty(value="页码")
	private int pageNum;
	@ApiModelProperty(value="任务名称")
	private String taskName;
	@ApiModelProperty(value="公司名称")
	private String companyName;
	@ApiModelProperty(value="发送时间")
	private String sendTime;
	@ApiModelProperty(value="发送方式")
	private Integer sendType;
	@ApiModelProperty(value="任务状态")
	private Integer sendStatus;
}
