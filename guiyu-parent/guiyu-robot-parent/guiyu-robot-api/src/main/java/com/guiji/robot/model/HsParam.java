package com.guiji.robot.model;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: HsParam 
* @Description: 话术信息
* @date 2018年11月19日 下午4:42:34 
* @version V1.0  
*/
@Data
@ApiModel(value="HsParam对象",description="话术对象")
public class HsParam {
	@ApiModelProperty(value="话术模板编号",required=true)
	private String templateId;
	@ApiModelProperty(value="话术模板参数map",required=true)
	private Map<String,String> paramMap;
}
