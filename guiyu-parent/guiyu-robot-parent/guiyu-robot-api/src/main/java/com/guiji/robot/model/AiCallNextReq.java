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
	private String seqId;
	@ApiModelProperty(value="用户编号",required=true)
	private String userId;
	@ApiModelProperty(value="机器人编号，开始拨打电话时分配的机器人",required=true)
	private String aiNo;
	@ApiModelProperty(value="话术模板编号")
	private String templateId;
	@ApiModelProperty(value="号码",required=false)
	private String phoneNo;
	@ApiModelProperty(value="播音状态（0-播音结束;1-播音中;999-开场白）",required=true)
	private String status;
	@ApiModelProperty(value="播音状态时(status=1)，time_stamp表示播音开始的时间，播音结束状态时(status=0),表示播音结束的时间",required=true)
	private long timestamp;
	@ApiModelProperty(value="等待秒数",required=false)
	private int waitCnt;
}