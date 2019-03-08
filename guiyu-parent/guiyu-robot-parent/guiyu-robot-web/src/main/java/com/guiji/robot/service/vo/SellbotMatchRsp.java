package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: Match 
* @Description: sellbot matched返回数据
* @date 2019年2月27日 下午6:04:31 
* @version V1.0  
*/
@Data
public class SellbotMatchRsp {
	//是否匹配
	private boolean matched;
	//是否打断后重新播放上段被打断录音（如果模板配置了“3秒5字”，且客户的语音没有被识别，且超过了模板中配置的打断字数，那么sellbot返回matched=1,match_keywords="repeat"）
	private boolean isRepeat;
	
}
