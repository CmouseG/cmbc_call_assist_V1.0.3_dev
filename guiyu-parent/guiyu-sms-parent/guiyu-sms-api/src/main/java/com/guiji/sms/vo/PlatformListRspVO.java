package com.guiji.sms.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="PlatformListRspVO对象",description="获取短信平台列表返回对象")
public class PlatformListRspVO
{
	@ApiModelProperty(value="总条数")
	private int totalCount;
	@ApiModelProperty(value="短信平台列表")
	private List<SmsPlatformVO> smsPlatformVOList;
}
