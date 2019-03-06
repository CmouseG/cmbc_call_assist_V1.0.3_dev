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
	//用户号
	private String userId;
	//机器人编号
	private String aiNo;
	//机器人昵称
	private String aiName;
	//机器人类型（1-老版本,2-飞龙）
	private int version;
	//机器人IP
	private String ip;
	//机器人端口
	private String port;
	//前端页面显示排序使用
	private int sortId;
	//话术模板
	private String templateIds;
	//初始化日期yyyy-MM-dd
	private String initDate;
	//初始化时间
	private String initTime;
	//机器人状态 F-空闲 ; B-忙 ; P-暂停不可用
	private String aiStatus;
	//正在拨打的电话
	private String callingPhone;
	//正在拨打电话的会话ID
	private String seqId;
	//正在拨打电话时间
	private String callingTime;
	//该机器人拨打数量
	private long callNum;
}