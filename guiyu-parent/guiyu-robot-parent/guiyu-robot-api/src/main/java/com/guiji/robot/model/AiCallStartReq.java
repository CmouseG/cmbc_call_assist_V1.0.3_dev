package com.guiji.robot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: AiCallStartReq 
* @Description: AI电话发起请求信息
* @date 2018年11月15日 下午1:48:26 
* @version V1.0  
*/
@Data
@ApiModel(value="AiCallStartReq对象",description="发起AI电话拨打请求")
public class AiCallStartReq {
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqid;
	@ApiModelProperty(value="用户编号",required=true)
	private Long userId;
	@ApiModelProperty(value="话术模板编号",required=true)
	private String templateId;
	@ApiModelProperty(value="号码",required=true)
	private String phoneNo;
	
}
