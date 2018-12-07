package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: AiTemplateVO 
* @Description: 机器人可用话术模板VO 
* @date 2018年12月7日 上午10:52:39 
* @version V1.0  
*/
@Data
public class AiTemplateVO {
	//话术模板ID
	private String templateId;
	//话术模板名称
	private String templateName;
	//是否需要TTS
	private boolean ttsFlag;
}
