package com.guiji.clm.vo;

import com.guiji.clm.dao.entity.SipLineBaseInfo;

import lombok.Data;

/** 
* @Description: 线路基本信息
* @Author: weiyunbo
* @date 2019年1月31日 下午1:11:30 
* @version V1.0  
*/
@Data
public class SipLineBaseInfoVO extends SipLineBaseInfo{
	//外显归属地名称
	private String overtAreaName;
	//地区名称
	private String areasName;
	//盲区名称
	private String exceptAreasName;
}