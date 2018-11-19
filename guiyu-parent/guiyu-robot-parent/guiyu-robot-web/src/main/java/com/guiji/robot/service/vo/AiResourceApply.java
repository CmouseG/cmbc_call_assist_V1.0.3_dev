package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: AiResourceApply 
* @Description: AI资源申请
* @date 2018年11月19日 下午6:23:21 
* @version V1.0  
*/
@Data
public class AiResourceApply {
	//用户ID
	private String userId;
	//话术模板
	private String templateId;
	//机器人申请数量
	private int aiNum;
}
