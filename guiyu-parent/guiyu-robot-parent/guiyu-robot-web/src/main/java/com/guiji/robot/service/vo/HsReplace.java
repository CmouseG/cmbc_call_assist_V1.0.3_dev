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
	
	/**打断设置**/
	private int silence_wait_secs = 2;	//静音的次数 (一轮中静音的次数，超过次数后挂断，默认2次)，sellbot已经处理了，超过2次静音后，返回end=1（挂断要求）
	private int silence_wait_time = 8;	//静音等待时间 (默认8S)
	private String[] non_interruptable;	//不打断域，多个域空格分隔
	private int interrupt_words_num = 3;	//打断的字数（最小值3，建议值5），小于这个字数忽略掉，不打断
	private int interrupt_min_interval = 3; //打断间隔时间（最小值3，建议值3）,不能连续打断
	
	
	/**飞龙版本**/
	private int version = 1;	//话术模板：1、默认1版本；2-飞龙版本
	private Map<String,String> replace_map_relationship;	//飞龙版本replace.json里参数新老映射关系（如："$0000":"客户姓名"）
	
}
