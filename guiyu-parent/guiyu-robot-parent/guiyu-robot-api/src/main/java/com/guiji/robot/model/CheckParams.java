package com.guiji.robot.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: CheckParamsReq 
* @Description: 参数校验请求model
* @date 2018年11月15日 上午10:53:17 
* @version V1.0  
*/
@Data
@ApiModel(value="CheckParams对象",description="参数校验请求")
public class CheckParams {
	@ApiModelProperty(value="要检查的话术模板",required=true)
	private List<HsParam> checkers;
}