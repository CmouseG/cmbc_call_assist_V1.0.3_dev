package com.guiji.robot.model;

import java.util.List;

import lombok.Data;

/** 
* @ClassName: TtsComposeCheckReq 
* @Description: TTS合成校验结果
* @date 2018年11月27日 下午2:30:22 
* @version V1.0  
*/
@Data
public class TtsComposeCheckRsp {
	//会话id
	private String seqId;
	//状态(P-合成中;S-完成;F-失败;N-查无数据)
	private String status;
	//TTS合成的语音列表
	private List<TtsVoice> ttsVoiceList;
}
