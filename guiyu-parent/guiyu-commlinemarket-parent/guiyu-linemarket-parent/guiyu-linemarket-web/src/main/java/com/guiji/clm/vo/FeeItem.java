package com.guiji.clm.vo;

import lombok.Data;

/** 
* @Description: 计费信息 
* @Author: weiyunbo
* @date 2019年1月30日 下午4:19:29 
* @version V1.0  
*/
@Data
public class FeeItem {
	/**
	 * 计费项ID
	 */
	private String chargingItemId;

	/**
	 * 计费项名称
	 */
	private String chargingItemName;

	/**
	 * 计费项类型 1-时长 2-路数 3-月度
	 */
	private Integer chargingType;

	/**
	 * 计费项单价，以“分”为单位
	 */
	private String price;

	/**
	 * 价格单位：1-秒 2-分钟 3-小时 4-天 5-月 6-年
	 */
	private Integer unitPrice;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 企业编号
	 */
	private String orgCode;

	/**
	 * 扣费标识 0-扣费 1-不扣费
	 */
	private Integer isDeducted;

	/**
	 * 状态  0-停用 1-启用
	 */
	private Integer status;
}
