package com.guiji.robot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: CheckAiReady 
* @Description: 拨打电话资源准备校验
* @date 2018年11月15日 下午12:00:01 
* @version V1.0  
*/
@Data
@ApiModel(value="CheckAiReady对象",description="拨打电话前资源准备询问")
public class CheckAiReady {
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqid;
	@ApiModelProperty(value="用户编号",required=true)
	private String userId;
	@ApiModelProperty(value="话术模板编号",required=true)
	private String templateId;
}
