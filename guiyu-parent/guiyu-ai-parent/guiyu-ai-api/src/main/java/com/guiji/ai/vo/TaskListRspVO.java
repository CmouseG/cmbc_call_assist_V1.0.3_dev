package com.guiji.ai.vo;

import java.util.List;

import com.guiji.ai.dao.entity.TtsStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TaskListRspVO对象",description="获取任务列表返回对象")
public class TaskListRspVO
{

	@ApiModelProperty(value="查询总数")
	private Integer totalNum;
	@ApiModelProperty(value="每页条数")
	private Integer pageSize = 10;
	@ApiModelProperty(value="任务列表")
	List<TtsStatus> ttsStatusList;
}
