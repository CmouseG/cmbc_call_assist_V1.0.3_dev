package com.guiji.ws.model;

import lombok.Data;

/** 
* @ClassName: WebSocketConnect 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @date 2019年2月25日 下午7:36:19 
* @version V1.0  
*/
@Data
public class WebSocketConnect {
	//公共参数
	private String uuid;	//连接的唯一编号
	private String sence;	//连接场景
	private String orgCode;	//用户所属真实企业编号
    private String userId;	//连接的用户编号
    
    /**其他个性化参数**/
  	private boolean assistCallUser;	//是否协呼人员
}
