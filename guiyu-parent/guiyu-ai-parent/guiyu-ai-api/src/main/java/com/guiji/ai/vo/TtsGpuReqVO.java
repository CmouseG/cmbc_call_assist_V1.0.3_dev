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
	@ApiModelProperty(value="ip")
	private String ip;
	@ApiModelProperty(value="port")
	private String port;
	@ApiModelProperty(value="pageSize")
	private Integer pageSize;
	@ApiModelProperty(value="pageNum")
	private Integer pageNum;
	
}
