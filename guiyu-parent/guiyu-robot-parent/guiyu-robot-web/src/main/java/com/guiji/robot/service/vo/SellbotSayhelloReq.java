package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: SellbotNextReq 
* @Description: sellbot客户语句响应服务
* @date 2018年11月16日 下午2:58:41 
* @version V1.0  
*/
@Data
public class SellbotSayhelloReq {
	//通话ID
	private String seqId;
	//用户语音或句子
	private String sentence;
	//含义见restore；正常逻辑暂未用；原来考虑多个电话共用一个端口，需要回传各个会话的当前状态，故有此参数；但后来决定采用每个端口处理一个电话的模式，此参数用不到；现在只用于跳转检查
	private String state = "state";  //按默认参数
	//静音超时事件
	private Boolean silence_exceed;
	//重复标识
	private String repeat;
	
	/**飞龙需要**/
	private String cfg; //话术模板
}