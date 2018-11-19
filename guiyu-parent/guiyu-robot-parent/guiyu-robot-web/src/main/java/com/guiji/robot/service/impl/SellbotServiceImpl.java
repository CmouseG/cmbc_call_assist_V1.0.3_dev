package com.guiji.robot.service.impl;

import com.guiji.robot.service.ISellbotService;
import com.guiji.robot.service.vo.SellbotMatchReq;
import com.guiji.robot.service.vo.SellbotRestoreReq;
import com.guiji.robot.service.vo.SellbotSayhelloReq;

/** 
* @ClassName: SellbotServiceImpl 
* @Description: Sellbot提供的服务 
* @date 2018年11月16日 下午3:07:22 
* @version V1.0  
*/
public class SellbotServiceImpl implements ISellbotService{

	/**
	 * sellbot初始化接口,每通电话前需要调用下初始化操作。
	 * @param sellbotRestoreReq
	 * @return
	 */
	public String restore(SellbotRestoreReq sellbotRestoreReq) {
		return null;
	}
	
	
	/**
	 * sellbot客户语句响应服务
	 * @param sellbotSayhelloReq
	 * @return
	 */
	public String sayhello(SellbotSayhelloReq sellbotSayhelloReq) {
		return null;
	}
	
	
	/**
	 * sellbot关键字查询匹配接口请求信息
	 * @param sellbotMatchReq
	 * @return
	 */
	public String match(SellbotMatchReq sellbotMatchReq) {
		return null;
	}
}
