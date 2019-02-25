package com.guiji.ws.model;

import lombok.Data;

/** 
* @ClassName: MonitorUser 
* @Description: 通话监控用户
* @date 2019年2月22日 下午6:06:24 
* @version V1.0  
*/
@Data
public class OnlineUser {
	//连接的唯一编号
	private String uuid;
	//监控的用户ID
	private String userId;
	//真实企业code
	private String orgCode;
	//是否协呼人员
	private boolean assistCallUser;
}
