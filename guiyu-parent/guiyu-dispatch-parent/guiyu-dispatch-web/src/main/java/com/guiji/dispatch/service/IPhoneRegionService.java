package com.guiji.dispatch.service;

/** 
* @Description: 手机号归属地服务
* @Author: weiyunbo
* @date 2019年1月30日 下午6:34:24 
* @version V1.0  
*/
public interface IPhoneRegionService {

	/**
	 * 获取手机号归属地
	 * 1、先从redis中获取
	 * 2、从DB获取（防止存量数据redis中没有）
	 * 3、到第三方查询
	 * @param phoneNo
	 * @return
	 */
	String queryPhoneRegion(String phoneNo);
}
