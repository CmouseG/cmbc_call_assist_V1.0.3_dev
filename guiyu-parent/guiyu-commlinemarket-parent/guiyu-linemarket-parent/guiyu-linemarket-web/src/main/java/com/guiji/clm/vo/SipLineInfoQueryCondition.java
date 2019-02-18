package com.guiji.clm.vo;

import java.util.List;

import lombok.Data;

/** 
* @Description: 第三方sip线路查询条件 
* @Author: weiyunbo
* @date 2019年1月23日 下午2:10:47 
* @version V1.0  
*/
@Data
public class SipLineInfoQueryCondition {
	private int pageNo;
	private int pageSize;
	//虚拟共享线路id
	private Integer sipShareId;
	//线路名称
	private String lineName;
	//创建人
	private String crtUser;
	//所属企业
	private String orgCode;
	//线路状态
	private Integer status; 
	//线路状态（多选）
	private List<Integer> statusList; 
}
