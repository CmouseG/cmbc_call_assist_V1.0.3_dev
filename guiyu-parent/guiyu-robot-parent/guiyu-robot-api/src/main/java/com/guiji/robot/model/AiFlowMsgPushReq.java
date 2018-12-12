package com.guiji.robot.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: AiFlowMsgPushReq 
* @Description: 通话过程流消息推送
* @date 2018年12月11日 下午1:56:38 
* @version V1.0  
*/
@Data
public class AiFlowMsgPushReq {
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqId;
	@ApiModelProperty(value="用户编号",required=true)
	private String userId;
	@ApiModelProperty(value="机器人编号，开始拨打电话时分配的机器人",required=true)
	private String aiNo;
	@ApiModelProperty(value="话术模板编号")
	private String templateId;
	@ApiModelProperty(value="用户说话的文字",required=true)
	private String sentence;
	@ApiModelProperty(value="通话音频文件url",required=false)
	private String wavfile;
	@ApiModelProperty(value="当前时间戳",required=true)
	private Long timestamp;
}
