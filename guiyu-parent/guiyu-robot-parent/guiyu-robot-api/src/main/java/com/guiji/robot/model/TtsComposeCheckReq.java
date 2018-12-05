package com.guiji.robot.model;

import java.util.List;

import lombok.Data;

/** 
* @ClassName: TtsComposeCheckReq 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @date 2018年12月5日 下午4:28:11 
* @version V1.0  
*/
@Data
public class TtsComposeCheckReq {
	private String templateId;
	List<String> seqIdList;
}
