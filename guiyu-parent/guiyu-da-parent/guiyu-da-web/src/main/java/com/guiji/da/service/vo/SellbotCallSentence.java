package com.guiji.da.service.vo;

import lombok.Data;

/** 
* @ClassName: SellbotCallSentence 
* @Description: sellbot返回的通话内容（一句句话）
* @date 2018年12月6日 下午2:39:23 
* @version V1.0  
*/
@Data
public class SellbotCallSentence {
	//当前域
	private String current_domain;
	//流程类型： 1: 主流程   2：一般问题   9：其他
	private String domain_type;
	//下个域
	private String next_domain;
	//客户说话
	private String sentence;
	//应答类型,0: 不拒绝   1：用户拒绝   9：未应答
	private String is_refused;
	//挂断类型 0: 未挂断   1：用户挂断   2：AI挂断
	private String hangup_type;
	//命中关键字
	private String keywords;
	//# 匹配类型：0:识别为空,1:未匹配,2：关键词匹配,3：静音超时,4：忽略,5：轮数超限,10：无匹配默认肯定,11：说'啊'默认肯定,12：无匹配且小于4个字默认肯定,99：未分类
	private String match_type;
	//
	private String match_method;
	//AI话术
	private String ai_answer;
	
}
