package com.guiji.clm.vo;

import com.guiji.clm.dao.entity.SipLineShare;

import lombok.Data;

/** 
* @Description: 共享线路列表
* @Author: weiyunbo
* @date 2019年1月31日 下午12:31:26 
* @version V1.0  
*/
@Data
public class SipLineShareVO extends SipLineShare{
	//接通率
	private Float callRate; 
	//接通率百分比
	private String callRatePercent;
	//单价中文
	private String univalentStr;
	//线路归属（自营、代理商、第三方）
	private String lineOwner;
	//外显归属地名称
	private String overtAreaName;
	//地区名称
	private String areasName;
	//盲区名称
	private String exceptAreasName;
}
