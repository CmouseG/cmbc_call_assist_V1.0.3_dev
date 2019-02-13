package com.guiji.clm.vo;

import java.util.List;

import com.guiji.clm.dao.entity.VoipGwInfo;

import lombok.Data;

/** 
* @Description: 语音网关VO对象 
* @Author: weiyunbo
* @date 2019年1月23日 下午5:30:44 
* @version V1.0  
*/
@Data
public class VoipGwInfoVO extends VoipGwInfo{
	//网关状态-VoipGwStatusEnum(1-工作中;2-不可用)
	private Integer gwStatus;
	private boolean beEnable; //设备是否启用
	private Integer chFreeNum;	//设备空闲通道数
	private Integer chUseNum;	//设备在忙的通道数
	private Integer chPutNum;	//可用通道数（闲+忙）插卡可用的通道数
	//网关端口信息
	List<VoipGwPortVO> portList;
}