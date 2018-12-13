package com.guiji.ai.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TtsGpuRspVO对象",description="查询GPU模型列表")
public class TtsGpuRspVO
{
	@ApiModelProperty(value="查询总数")
	private Integer totalNum;
	@ApiModelProperty(value="GPU列表")
	private List<TtsModelGpuVO> ttsGpuList;
	
}
