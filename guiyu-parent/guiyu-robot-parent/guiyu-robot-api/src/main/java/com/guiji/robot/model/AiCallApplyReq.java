package com.guiji.robot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: AiCallApplyReq 
* @Description: 机器人资源申请
* @date 2018年11月26日 上午11:49:28 
* @version V1.0  
*/
@Data
@ApiModel(value="AiCallStartReq对象",description="发起AI电话拨打请求")
public class AiCallApplyReq {
	@ApiModelProperty(value="会话ID-调度中心会话id",required=true)
	private String disSeqId;
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqId;
	@ApiModelProperty(value="用户编号",required=true)
	private String userId;
	@ApiModelProperty(value="话术模板编号",required=true)
	private String templateId;
	@ApiModelProperty(value="号码",required=true)
	private String phoneNo;
}
