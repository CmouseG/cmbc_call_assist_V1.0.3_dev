package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: UserResourceCache 
* @Description: 用户资源缓存
* @date 2018年11月19日 下午5:08:22 
* @version V1.0  
*/
@Data
public class UserResourceCache {
	//用户ID
	private String userId;
	//机器人总数量
	private int aiNum;
	//机器人资源变更状态：0-减少；1-增加
	private String chgStatus;
}
