package com.guiji.robot.model;

import java.util.Map;

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
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqid;
	@ApiModelProperty(value="话术模板编号",required=true)
	private String templateId;
	@ApiModelProperty(value="话术模板参数map",required=true)
	private Map<String,String> paramMap;
}