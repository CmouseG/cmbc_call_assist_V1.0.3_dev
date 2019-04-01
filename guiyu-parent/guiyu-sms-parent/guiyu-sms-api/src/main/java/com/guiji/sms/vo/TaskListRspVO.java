package com.guiji.sms.vo;

import java.util.List;

import com.guiji.sms.dao.entity.SmsTask;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TaskListRspVO对象",description="新增短信任务返回对象")
public class TaskListRspVO
{
	@ApiModelProperty(value="总条数")
	private Long totalCount;
	@ApiModelProperty(value="配置列表")
	private List<SmsTask> smsTaskList;
}
