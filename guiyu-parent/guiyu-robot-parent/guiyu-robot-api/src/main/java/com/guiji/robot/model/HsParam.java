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
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqid;
	@ApiModelProperty(value="话术模板编号",required=true)
	private String templateId;
	@ApiModelProperty(value="话术模板参数,多参数竖线|分隔",required=true)
	private String params;
	@ApiModelProperty(value="业务逻辑使用，接口不需要传",hidden=true)
	private Map<String,String> paramMap;
}
