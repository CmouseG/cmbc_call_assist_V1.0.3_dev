package com.guiji.clm.vo;

import com.guiji.clm.dao.entity.SipLineApply;

import lombok.Data;

/** 
* @Description: 线路申请、审批
* @Author: weiyunbo
* @date 2019年2月1日 下午3:25:09 
* @version V1.0  
*/
@Data
public class SipLineApplyVO extends SipLineApply{
	//申请人名称
	private String applyUserName;
	//申请企业名称
	private String applyOrgName;
	//申请线路的外显归属地名称
	private String overtAreaName;
	//申请线路的地区名称
	private String areasName;
	//申请线路的盲区名称
	private String exceptAreasName;
	//申请的线路归属（自营、代理商、第三方）
	private String lineOwner;
}
