package com.guiji.clm.vo;

import com.guiji.clm.dao.entity.SipLineExclusive;

import lombok.Data;

/** 
* @Description: 独享线路VO
* @Author: weiyunbo
* @date 2019年2月1日 上午10:19:03 
* @version V1.0  
*/
@Data
public class SipLineExclusiveVO extends SipLineExclusive{
	//外显归属地名称
	private String overtAreaName;
	//地区名称
	private String areasName;
	//盲区名称
	private String exceptAreasName;
	//线路归属
	private String lineOwner;
	//单价中文
	private String univalentStr;
	
}
