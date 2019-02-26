package com.guiji.robot.service.vo;

import java.util.Date;

import lombok.Data;

/** 
* @ClassName: CallSentence 
* @Description: 一通电话中每个对话句子
* @date 2019年2月20日 下午7:50:18 
* @version V1.0  
*/
@Data
public class CallSentence {
	//客户说的话
	private String sentence;
	//回复
	private String answer;
	//意图：A/B/C...
	private String intent;
	//对话类型：1-AI;2-客户
	private int diaType;
	//是否打断后的对话-用于判断打断
	private boolean interruptFlag;
	//当前域名：开场白、一般问题、出错 、其他。。。
	private String answered_domain;
	//当前状态：开场白、一般问题、出错 、其他。。。（不知道和answered_domain的区别，尽量使用answered_domain吧）
	private String state;
	//回复话术模板分支信息
	private String answered_branch;
	//客户说的话命中的关键字
	private String keywords;
	//客户话的分词
	private String word_segment_result;
	//挂断标志: 0-正常继续;1-会话结束;2-转人工
	private int end;
	//匹配类型：0-识别为空;1-未匹配;2-关键词匹配;3-静音超时;4-忽略;5-轮数超限;10-无匹配默认肯定;11-说'啊'默认肯定;12-无匹配且小于4个字默认肯定;99-未分类
	private String match_type;
	//判定为上面意向的原因
	private String reason;
	//本次响应时间
	private Long used_time_ms;
	//用户关注话题
	private String user_attentions;
	//本次播放的语音文件名称
	private String wav_filename;
	
	//记录时间
	private Date recordTime;
}
