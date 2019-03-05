package com.guiji.clm.vo;

import com.guiji.clm.dao.entity.VoipGwPort;

import lombok.Data;

/** 
* @Description: 语音网关VO对象 
* @Author: weiyunbo
* @date 2019年1月23日 下午5:30:44 
* @version V1.0  
*/
@Data
public class VoipGwPortVO extends VoipGwPort{
	//端口sip注册状态
	private Integer portRegStatus;
	//端口工作状态
	private Integer portWorkStatus;
	//端口基站连接状态
	private Boolean portConnFlag;
	//网络信号值(0-100)
	private Integer loadType;
	//网关里配置的手机卡
	private String gwPhoneNo;
	//账号是否匹配--网关配置端口sip账号，集中管理后需要将端口分配对应的账号
	private boolean isSipMatched;
	//客户名称
	private String userName;
}