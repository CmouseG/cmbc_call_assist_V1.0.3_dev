package com.guiji.ai.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TaskListRspVO对象",description="获取任务列表返回结果")
public class TaskListRspVO
{
	@ApiModelProperty(value="主键Id")
	private Integer id;
	@ApiModelProperty(value="业务id")
    private String busId;
	@ApiModelProperty(value="模型名称")
    private String model;
	@ApiModelProperty(value="状态")
    private String status;
	@ApiModelProperty(value="文本数量")
    private Integer text_count;
	@ApiModelProperty(value="创建时间")
    private Date createTime;
	@ApiModelProperty(value="更新时间")
    private Date updateTime;
}
