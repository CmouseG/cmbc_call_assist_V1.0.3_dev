package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: UserAiCfgQueryCondition 
* @Description: 用户机器人配置查询条件
* @date 2018年11月27日 上午10:59:56 
* @version V1.0  
*/
@Data
public class UserAiCfgQueryCondition {
	//用户id
	private String userId;
	//话术模板
	private String templateId;
}
