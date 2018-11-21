package com.guiji.robot.constants;

/** 
* @ClassName: RobotConstants 
* @Description: AI机器人能力中心常量类
* @date 2018年11月15日 下午8:26:43 
* @version V1.0  
*/
public class RobotConstants {
	
	//机器人资源变更
	public static final String USER_CHG_STATUS_A = "1"; //增加
	public static final String USER_CHG_STATUS_S = "0"; //减少
	
	//用户机器人账户状态
	public static final String USER_CFG_STATUS_S = "S"; //S-正常
	public static final String USER_CFG_STATUS_V = "V"; //V-正常
	
	//机器人状态
	public static final String AI_STATUS_F = "F"; //F-空闲
	public static final String AI_STATUS_B = "B"; //B-忙
	public static final String AI_STATUS_P = "P"; //P-暂停分配
	
	public static final String RSP_CODE_SUCCESS = "0";	//外部请求返回的成功状态码
	
	/**
	 * 分配的机器人缓存
	 */
	public static final String ROBOT_ASSIGN_AI = "USER_AI_";
	
	/**
	 * 用户机器人资源
	 */
	public static final String ROBOT_USER_RESOURCE = "ROBOT_USER_RESOURCE";
}
