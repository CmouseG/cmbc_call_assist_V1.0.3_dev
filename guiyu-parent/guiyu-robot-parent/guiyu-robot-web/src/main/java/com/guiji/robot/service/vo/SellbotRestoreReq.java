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
	//是否大客户--默认直接写死true（sellbot有不同处理，比如：不校验TTS是否合成-不走备用话术，返回要播放的语音地址不是全路径等）
	private boolean big_customer = true;
	//电话号码
	private String phonenum;
	//给sellbot用来合成tts完整sentence变量 name|money|date
//	private String key;
	//给sellbot用来合成tts完整sentence变量
	private String val;
}
