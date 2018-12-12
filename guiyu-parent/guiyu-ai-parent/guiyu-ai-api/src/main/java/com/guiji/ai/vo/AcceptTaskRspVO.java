package com.guiji.ai.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="AcceptTaskRspVO",description="累计接受任务返回对象")
public class AcceptTaskRspVO
{
	@ApiModelProperty(value="查询总数")
	private Integer totalNum;
	@ApiModelProperty(value="每页条数")
	private Integer pageSize = 10;
	@ApiModelProperty(value="任务数")
	private List<AcceptTaskVO> taskNums;
	
}
