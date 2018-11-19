package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: TtsWavCache 
* @Description: TTS已经合成的语音数据缓存
* @date 2018年11月16日 上午9:45:54 
* @version V1.0  
*/
@Data
public class TtsWavCache {
	//话术模板编号
	private String templateId;
	//TTS语音key
	private String ttsKey;
	//参数key
	private String ttsParamKeys;
	//参数值
	private String ttsParamValues;
	//tts合成后的语音url
	private String url;
	//创建时间yyyyMMddHHmmss
	private String crtTime;
}
