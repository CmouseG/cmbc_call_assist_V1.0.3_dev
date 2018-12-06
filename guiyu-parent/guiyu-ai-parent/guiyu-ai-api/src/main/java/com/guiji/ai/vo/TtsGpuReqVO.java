package com.guiji.ai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TtsGpuReqVO对象",description="查询GPU模型列表分页条件")
public class TtsGpuReqVO
{
	@ApiModelProperty(value="pageSize")
	private int pageSize;
	@ApiModelProperty(value="pageNum")
	private int pageNum;
	
}
