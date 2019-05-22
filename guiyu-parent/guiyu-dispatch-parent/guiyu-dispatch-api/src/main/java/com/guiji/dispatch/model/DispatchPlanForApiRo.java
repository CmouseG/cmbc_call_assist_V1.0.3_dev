package com.guiji.dispatch.model;

import lombok.Data;

import java.util.List;

@Data
public class DispatchPlanForApiRo {

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 批次名
	 */
	private String batchName;

	/**
	 * 号码列表
	 */
	private List<PhoneRo> phoneRoList;

	/**
	 * 模板id
	 */
	private String robot;

	/**
	 * 线路id
	 */
	private List<Integer> lineIds;

	/**
	 * 坐席组id
	 */
	private String callAgent;

	/**
	 * 是否当日清除
	 */
	private Integer clean;

	/**
	 * 呼叫日期
	 */
	private Integer callData;

	/**
	 * 呼叫时间
	 */
	private String callHour;

	/**
	 * 批次回调url
	 */
	private String batchCallBackUrl;

	/**
	 * 单个回调url
	 */
	private String singleCallBackUrl;

}
