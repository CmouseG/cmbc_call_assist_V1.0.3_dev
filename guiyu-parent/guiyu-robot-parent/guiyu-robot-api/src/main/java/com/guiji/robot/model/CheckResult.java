package com.guiji.robot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: CheckParamsRsp 
* @Description: 参数校验相应model
* @date 2018年11月15日 上午11:09:45 
* @version V1.0  
*/
@Data
@ApiModel(value="CheckResult对象",description="参数校验请求")
public class CheckResult {
	@ApiModelProperty(value="是否校验通过,true:通过，false：校验失败",required=true)
	private boolean isPass;
	@ApiModelProperty(value="校验结果描述，一般校验失败会返回描述")
	private boolean checkMsg;
}