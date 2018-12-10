package com.guiji.ai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TtsGpuReqVO对象",description="查询GPU模型列表")
public class TtsGpuReqVO
{
	@ApiModelProperty(value="模型名称")
	private String model;
	@ApiModelProperty(value="IP地址")
	private String ip;
	@ApiModelProperty(value="端口")
	private String port;
	@ApiModelProperty(value="每页条数")
	private Integer pageSize;
	@ApiModelProperty(value="页码")
	private Integer pageNum;
	
}
