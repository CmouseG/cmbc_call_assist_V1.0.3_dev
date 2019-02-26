package com.guiji.robot.service.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

/** 
* @ClassName: CallInfo 
* @Description: 一通电话
* @date 2019年2月20日 下午7:39:11 
* @version V1.0  
*/
@Data
public class CallInfo {
	//会话ID
	private String seqId;
	//会话ID-调度中心会话id
	private String disSeqId;
	//用户id
	private String userId;
	//机器人编号
	private String aiNo;
	//话术模板
	private String templateId;
	//是否可以协呼
	private boolean assistFlag;
	//手机号
	private String phoneNo;
	//对话轮数
	private int dialogCount;
	//呼叫起始时间
	private Date callStartTime;
	//挂断时间
	private Date callEndTime;
	//意向级别A/B/C..
	private String intentLevel;
	//话术当前域
	private String current_domain;
	//通话编号（只是为了在前端长链接展示时排序使用）
	private long incr;
	//对话list
	private List<CallSentence> sentenceList;
	
	//显示使用字段
	//机器人名称
	private String aiName;
}
