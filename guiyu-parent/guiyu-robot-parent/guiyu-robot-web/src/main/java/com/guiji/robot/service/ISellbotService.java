package com.guiji.robot.service;

import com.guiji.robot.model.HangupRes;
import com.guiji.robot.service.vo.*;

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
	String restore(AiBaseInfo ai,SellbotRestoreReq sellbotRestoreReq);
	
	
	/**
	 * sellbot客户语句响应服务
	 * @param sellbotSayhelloReq
	 * @return
	 */
	String sayhello(AiBaseInfo ai,SellbotSayhelloReq sellbotSayhelloReq);
	
	
	/**
	 * sellbot关键字查询匹配接口请求信息
	 * @param sellbotMatchReq
	 * @return
	 */
	String match(AiBaseInfo ai,SellbotMatchReq sellbotMatchReq);
	
	/**
	 * 电话挂断后做数据清理（目前只有飞龙需要，sellbot不需要，为接口统一需要统一封装下）
	 * @param endReq
	 * @return
	 */
	HangupRes clean(AiInuseCache aiInuseCache, EndReq endReq);
}
