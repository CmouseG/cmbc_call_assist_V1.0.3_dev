package com.guiji.da.constants;

/** 
* @ClassName: RobotConstants 
* @Description: 量化分析常量类
* @date 2018年12月6日 下午8:26:43 
* @version V1.0  
*/
public class DaConstants {
	
	//redis key
	public static final String DA_CALL_PROCESS_STAT_ = "DA_CALL_PROCESS_STAT_"; //按用户统计当天的数据流程统计(key不要随便改，有字段截取)
	
	//域类型 1-主流程;2-一般问题;9-其他
	public static final String DOMAIN_TYPE_MAIN = "1";	//主流程
	public static final String DOMAIN_TYPE_COMMON = "2";	//一般问题
	public static final String DOMAIN_TYPE_OTHER = "9";	//其他
	
	//拒绝类型
	public static final String REFUSED_NO = "0";	//不拒绝
	public static final String REFUSED_YES = "1";	//用户拒绝
	public static final String REFUSED_NON = "9";	//无应答
	
	//挂断类型
	public static final String HANGUP_NO = "0";	//未挂断
	public static final String HANGUP_YES = "1";	//用户挂断
	public static final String HANGUP_AI = "2";	//ai挂断
	
}
