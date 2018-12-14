package com.guiji.robot.service.vo;

import com.guiji.robot.dao.entity.UserAiCfgBaseInfo;

import lombok.Data;

/** 
* @ClassName: UserAiCfgBaseInfoVO 
* @Description: 机器人配置基本信息
* @date 2018年11月27日 下午3:33:07 
* @version V1.0  
*/
@Data
public class UserAiCfgBaseInfoVO extends UserAiCfgBaseInfo{
	//用户名称
	private String userName;
	//已分配的机器人数量
	private int assignNum;
	//创建用户名称
	private String crtUserName;
	//更新用户名称
	private String updateUserName;
	
}
