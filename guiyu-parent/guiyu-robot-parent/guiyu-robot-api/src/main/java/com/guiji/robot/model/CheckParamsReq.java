package com.guiji.robot.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: CheckParamsReq 
* @Description: 参数检查请求
* @date 2018年11月23日 下午3:41:38 
* @version V1.0  
*/
@Data
@ApiModel(value="CheckParamsReq对象",description="参数检查请求")
public class CheckParamsReq {
	@ApiModelProperty(value="是否需要同步做资源准备（TTS）",required=true)
	private boolean needResourceInit;
	@ApiModelProperty(value="需要检查的业务数据",required=true)
	private List<HsParam> checkers;
}
