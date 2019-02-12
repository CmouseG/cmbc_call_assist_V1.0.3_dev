package com.guiji.clm.vo;

import lombok.Data;

/** 
* @Description: sip线路(独享)查询条件
* @Author: weiyunbo
* @date 2019年1月23日 上午10:40:47 
* @version V1.0  
*/
@Data
public class SipLineExclusiveQueryCondition {
	private int pageNo;
	private int pageSize;
	//代理线路编号
	private Integer agentLineId;
	//原sip线路编号
	private Integer sipLineId;
	//线路类型(1-自营线路;2-代理线路)
	private Integer lineType;
	//线路状态(1-正常;2-到期;3-失效)
	private Integer lineStatus;
	//归属人
	private String userId;
	//归属企业
	private String orgCode;
}