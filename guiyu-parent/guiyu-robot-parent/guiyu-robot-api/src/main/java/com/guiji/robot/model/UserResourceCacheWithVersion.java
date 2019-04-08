package com.guiji.robot.model;

import lombok.Data;

import java.util.Map;

/** 
* @ClassName: UserResourceCache 
* @Description: 用户资源缓存
* @date 2018年11月19日 下午5:08:22 
* @version V1.0  
*/
@Data
public class UserResourceCacheWithVersion {
	//用户ID
	private String userId;
	//机器人总数量
	private int aiNum;
	//机器人资源变更状态：S-减少；A-增加
	private String chgStatus;
	//用户按模板拆分的机器人数量配置
	Map<String,TemplateInfo> tempInfoMap;
}
