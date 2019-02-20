package com.guiji.clm.model;

import java.math.BigDecimal;

import lombok.Data;

/** 
* @Description: 独享线路信息
* @Author: weiyunbo
* @date 2019年1月31日 上午10:07:24 
* @version V1.0  
*/
@Data
public class SipLineVO {
	private Integer id;
	//线路id(呼叫中心使用)
	private Integer lineId;
	//线路名称
	private String lineName;
	//供应商
	private String supplier;
	//并发数
	private Integer maxConcurrentCalls;
	//单价（元/分钟）
	private BigDecimal univalent;
	//外显归属地
	private String overtArea;
	//行业限制
    private String industrys;
    //模板限制
    private String templates;
    //地区限制
    private String areas;
    //地区盲区
    private String exceptAreas;
    //用户号
    private String userId;
    //归属企业
    private String orgCode;
    //外显归属地名称
  	private String overtAreaName;
  	//地区名称
  	private String areasName;
  	//盲区名称
  	private String exceptAreasName;
  	//线路计费类型:1-自营线路(扣费),2-客户自备线路(不扣费)
  	private int lineFeeType;
}
