package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: TtsTempData 
* @Description: tts合成过程中需要的临时对象 
* @date 2018年11月20日 下午5:53:33 
* @version V1.0  
*/
@Data
public class TtsTempData {
	//话术模板ID
	private String templateId;
	//需合成的内容key
	private String ttsPosKey;
	//参数替换前文本内容
	private String ttsPosContent;
	//参数替换后文本内容
	private String ttsContent;
	//TTS工具生成语音后的url
	private String audioUrl;
	//TTS工具生成语音后的本地wav路径
	private String audioFilePath;
}
