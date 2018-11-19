package com.guiji.robot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: AiCallNextReq 
* @Description: AI电话客户语音响应请求信息
* @date 2018年11月15日 下午2:05:18 
* @version V1.0  
*/
@Data
@ApiModel(value="AiCallNextReq对象",description="AI电话客户语音响应请求信息")
public class AiCallNextReq {
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqid;
	@ApiModelProperty(value="用户编号")
	private String userId;
	@ApiModelProperty(value="机器人编号，开始拨打电话时分配的机器人",required=true)
	private String aiNo;
	@ApiModelProperty(value="话术模板编号",required=true)
	private String templateId;
	@ApiModelProperty(value="号码",required=true)
	private String phoneNo;
	@ApiModelProperty(value="用户说话的文字",required=true)
	private String sentence;
}