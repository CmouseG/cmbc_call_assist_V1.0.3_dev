package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: SellbotRestore 
* @Description: sellbot初始化接口，每通电话前需要调用下初始化操作。
* @date 2018年11月16日 下午2:56:08 
* @version V1.0  
*/
@Data
public class SellbotRestoreReq {
	//模板编号
	private String cfg;
	//会话ID
	private String seqid;
	//电话号码
	private String phonenum;
}
