package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: SellbotMatchReq 
* @Description: sellbot关键字查询匹配接口请求信息
* @date 2018年11月16日 下午3:03:08 
* @version V1.0  
*/
@Data
public class SellbotMatchReq {
	/**sellbot需要属性**/
	//用来匹配的用户的话的文本
	private String sentence;
	
	/**飞龙需要属性**/
	//会话id
	private String seqId;
	//话术模板
	private String cfg;
}
