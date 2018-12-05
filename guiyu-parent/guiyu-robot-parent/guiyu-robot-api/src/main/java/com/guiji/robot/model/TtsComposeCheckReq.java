package com.guiji.robot.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: TtsComposeCheckReq 
* @Description: tts查询 
* @date 2018年12月5日 下午4:28:11 
* @version V1.0  
*/
@Data
public class TtsComposeCheckReq {
	@ApiModelProperty(value="话术模板编号",required=true)
	private String templateId;
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqId;
}
