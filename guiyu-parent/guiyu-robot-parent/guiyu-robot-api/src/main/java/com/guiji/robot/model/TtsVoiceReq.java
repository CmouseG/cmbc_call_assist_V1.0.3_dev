package com.guiji.robot.model;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: TtsSource 
* @Description: TTS语音合成请求信息
* @date 2018年11月15日 下午1:30:13 
* @version V1.0  
*/
@Data
@ApiModel(value="TtsSynthesisReq对象",description="TTS语音合成请求")
public class TtsVoiceReq {
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqid;
	@ApiModelProperty(value="用户编号",required=true)
	private Long userId;
	@ApiModelProperty(value="话术模板编号",required=true)
	private String templateId;
	@ApiModelProperty(value="话术模板参数map",required=true)
	private Map<String,String> paramMap;
}