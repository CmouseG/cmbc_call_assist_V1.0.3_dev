package com.guiji.robot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: TtsVoice 
* @Description: TTS语音
* @date 2018年11月15日 下午1:40:38 
* @version V1.0  
*/
@Data
@ApiModel(value="TtsVoice对象",description="TTS语音合成后的资源地址")
public class TtsVoice {
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqid;
	@ApiModelProperty(value="tts语音对应的key",required=true)
	private	String ttsKey;
	@ApiModelProperty(value="tts语音存放的文件服务器url",required=true)
	private String ttsUrl;
}