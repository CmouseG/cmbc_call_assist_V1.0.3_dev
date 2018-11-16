package com.guiji.robot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: AiCallLngKeyHitReq 
* @Description: 话术关键字命中匹配请求
* @date 2018年11月16日 下午2:08:59 
* @version V1.0  
*/
@Data
@ApiModel(value="AiCallLngKeyMatchReq对象",description="话术关键字命中匹配请求")
public class AiCallLngKeyMatchReq {
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqid;
	@ApiModelProperty(value="用户编号")
	private Long userId;
	@ApiModelProperty(value="机器人编号，开始拨打电话时分配的机器人",required=true)
	private String aiNo;
	@ApiModelProperty(value="话术模板编号",required=true)
	private String templateId;
	@ApiModelProperty(value="号码",required=true)
	private String phoneNo;
	@ApiModelProperty(value="用户说话的文字",required=true)
	private String sentence;
}