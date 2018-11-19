package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: AiInuseCache 
* @Description: 目前已经分配的AI机器人缓存数据
* @date 2018年11月16日 上午9:49:49 
* @version V1.0  
*/
@Data
public class AiInuseCache {

	//分配ID
	private String assignId;
	//用户号
	private String userId;
	//机器人编号
	private String aiNo;
	//机器人IP
	private String ip;
	//机器人端口
	private String port;
	//话术模板
	private String templateId;
	//初始化日期yyyy-MM-dd
	private String initDate;
	//初始化时间
	private String initTime;
	
	private String aiStatus;
	
	private String callingPhone;
	
	private long callNum;
}
