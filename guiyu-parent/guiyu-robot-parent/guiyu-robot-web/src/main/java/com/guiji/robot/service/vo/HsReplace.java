package com.guiji.robot.service.vo;

import java.util.Map;

import lombok.Data;

/** 
* @ClassName: HsReplace 
* @Description: 话术模板-对应的是replace.json文件
* @date 2018年11月20日 下午3:54:43 
* @version V1.0  
*/
@Data
public class HsReplace {
	/**common.js**/
	private String templateName;	//模板名称
	private String templateId;	//模板编号
	private String trade;	//行业
//	private boolean tts;	//是否需要tts(如果common.json文件为空，不好判断是否需要tts，所以还是使用replace.json判断吧)
	private boolean agent;	//是否转人工
	
	/**replace.json**/
	private boolean template_tts_flag;  //这几个都表示是否需要tts合成
	private String use_speaker_flag;	//模型编号（录音师）
	private String[] num_sentence_merge_lst; //几段需要tts合成的录音wav文件id
	private Map<String,String[]> rec_tts_wav; //每段tts wav语音需要哪几段语音合并
	private Map<String,String> tts_pos; //每个支段录音的文本（带参）
	private String[] replace_variables_flag;	//本模板需要的参数
	private String[] replace_variables_type;	//本模板需要的参数类型
}
