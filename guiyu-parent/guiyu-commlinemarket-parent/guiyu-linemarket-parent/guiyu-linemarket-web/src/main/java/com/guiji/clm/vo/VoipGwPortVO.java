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
}