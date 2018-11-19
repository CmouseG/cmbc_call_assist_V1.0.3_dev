package com.guiji.robot.service;

import com.guiji.robot.service.vo.SellbotMatchReq;
import com.guiji.robot.service.vo.SellbotRestoreReq;
import com.guiji.robot.service.vo.SellbotSayhelloReq;

/** 
* @ClassName: ISellbotService 
* @Description: Sellbot提供的服务
* @date 2018年11月16日 下午3:04:31 
* @version V1.0  
*/
public interface ISellbotService {
	
	
	/**
	 * sellbot初始化接口,每通电话前需要调用下初始化操作。
	 * @param sellbotRestoreReq
	 * @return
	 */
	String restore(SellbotRestoreReq sellbotRestoreReq);
	
	
	/**
	 * sellbot客户语句响应服务
	 * @param sellbotSayhelloReq
	 * @return
	 */
	String sayhello(SellbotSayhelloReq sellbotSayhelloReq);
	
	
	/**
	 * sellbot关键字查询匹配接口请求信息
	 * @param sellbotMatchReq
	 * @return
	 */
	String match(SellbotMatchReq sellbotMatchReq);
}
