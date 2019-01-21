package com.guiji.sms.vo;

import java.util.List;

import com.guiji.sms.dao.entity.SmsTaskDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TaskDetailListRspVO对象",description="获取短信详情列表返回对象")
public class TaskDetailListRspVO
{
	@ApiModelProperty(value="总条数")
	private int totalCount;
	@ApiModelProperty(value="短信详情列表")
	private List<SmsTaskDetail> smsTaskDetailList;
}
