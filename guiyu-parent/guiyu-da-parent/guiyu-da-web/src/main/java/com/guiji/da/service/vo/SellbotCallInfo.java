package com.guiji.da.service.vo;

import java.util.List;

import lombok.Data;

/** 
* @ClassName: RobotCallInfo 
* @Description: Sellbot返回的通话详情 
* @date 2018年12月6日 下午2:49:13 
* @version V1.0  
*/
@Data
public class SellbotCallInfo {
	//sellbot code
	private String code;
	//sellbot key
	private String key;
	//话术模板id
	private String cfg_name;
	//机器码
	private String mcode;
	//会话ID
	private String seqid;
	//通话内容list
	private List<SellbotCallSentence> data;
		
}
