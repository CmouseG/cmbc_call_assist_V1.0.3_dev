package com.guiji.da.service.robot;

/** 
* @ClassName: SellbotCallbackService 
* @Description: 接收Sellbot通话记录回调服务
* @date 2018年12月6日 下午2:27:43 
* @version V1.0  
*/
public interface ISellbotCallbackService {

	
	/**
	 * 接收Sellbot每通电话的回调数据（数据主要包括来回的对话）
	 * @param sellbotJson
	 */
	void receiveSellbotCallback(String sellbotJson);
}
