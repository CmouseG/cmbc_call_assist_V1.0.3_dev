package com.guiji.clm.vo;

import lombok.Data;

/** 
* @Description: voip网关查询条件
* @Author: weiyunbo
* @date 2019年1月23日 上午10:40:47 
* @version V1.0  
*/
@Data
public class VoipGwQueryCondition {
	private int pageNo;
	private int pageSize;
	//网关名称
	private String gwName;
	//网关品牌
	private String gwBrand;
	//注册服务IP
	private String sipIp;
	//归属企业
	private String orgCode;
}
