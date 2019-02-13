package com.guiji.clm.vo;

import lombok.Data;

/** 
* @Description: 语音网关查询条件
* @Author: weiyunbo
* @date 2019年1月23日 上午10:40:47 
* @version V1.0  
*/
@Data
public class SipLineShareQueryCondition {
	private int pageNo;
	private int pageSize;
	
	//线路状态：1-正常;2-到期;3-失效
	private Integer lineStatus;
	//行业
	private String industry;
	//归属企业
	private String orgCode;
}
