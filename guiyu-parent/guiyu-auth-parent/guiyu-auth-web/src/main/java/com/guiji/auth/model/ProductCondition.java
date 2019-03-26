package com.guiji.auth.model;

import java.util.List;

import lombok.Data;

/** 
* @ClassName: ProductCondition 
* @Description: 产品查询条件
* @auth weiyunbo
* @date 2019年3月11日 上午10:25:34 
* @version V1.0  
*/
@Data
public class ProductCondition {
	private int pageNo;
	private int pageSize;
	//产品名称
	private String name;
	//产品描述
	private String productDesc;
	//状态
	private List<Integer> productStatus;
}
