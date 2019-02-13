package com.guiji.clm.vo;

import lombok.Data;

/** 
* @Description: 第三方sip线路查询条件 
* @Author: weiyunbo
* @date 2019年1月23日 下午2:10:47 
* @version V1.0  
*/
@Data
public class SipLineInfoQueryCondition {
	//虚拟共享线路id
	private Integer sipShareId;
	//线路名称
	private String lineName;
	//所属用户
	private String belongUser;
	//所属企业
	private String orgCode;
}
