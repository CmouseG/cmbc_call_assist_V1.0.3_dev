package com.guiji.robot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: AiHangupReq 
* @Description: AI电话挂断请求信息
* @date 2018年11月15日 下午2:23:48 
* @version V1.0  
*/
@Data
@ApiModel(value="AiHangupReq对象",description="电话挂断后调用释放机器人")
public class AiHangupReq {
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqid;
	@ApiModelProperty(value="用户编号",required=true)
	private Long userId;
	@ApiModelProperty(value="号码",required=true)
	private String phoneNo;
	@ApiModelProperty(value="机器人编号",required=true)
	private String aiNo;
}