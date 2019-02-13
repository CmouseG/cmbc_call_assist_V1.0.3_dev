package com.guiji.clm.model;

import java.util.List;

import lombok.Data;

/** 
* @Description: SIP线路路由VO
* @Author: weiyunbo
* @date 2019年1月30日 下午5:23:03 
* @version V1.0  
*/
@Data
public class SipRouteRuleVO {
	private Integer id;	//规则id
	private String userId;	//用户id
	private String ruleContent;	//规则内容
	private List<SipRouteItemVO> itemList; 
}
