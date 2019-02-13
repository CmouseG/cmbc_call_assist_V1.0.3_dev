package com.guiji.clm.model;

import lombok.Data;

/** 
* @Description: SIP线路路由规则项
* @Author: weiyunbo
* @date 2019年1月30日 下午5:26:14 
* @version V1.0  
*/
@Data
public class SipRouteItemVO {
	private String itemCode;
	private String itemName;
	private boolean selected;
}
