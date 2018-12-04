package com.guiji.robot.constants;

/** 
* @ClassName: RobotConstants 
* @Description: AI机器人能力中心常量类
* @date 2018年11月15日 下午8:26:43 
* @version V1.0  
*/
public class RobotConstants {
	
	//机器人资源变更
	public static final String USER_CHG_STATUS_A = "A"; //增加
	public static final String USER_CHG_STATUS_S = "S"; //减少
	
	//用户机器人账户状态
	public static final String USER_CFG_STATUS_S = "S"; //S-正常
	public static final String USER_CFG_STATUS_V = "V"; //V-失效
	
	//用户账号变更历史-操作
	public static final String HANDLE_TYPE_A = "A"; //新增
	public static final String HANDLE_TYPE_U = "U"; //更新
	public static final String HANDLE_TYPE_D = "D"; //删除
	
	//机器人状态
	public static final String AI_STATUS_F = "F"; //F-空闲
	public static final String AI_STATUS_B = "B"; //B-忙
	public static final String AI_STATUS_P = "P"; //P-暂停分配
	
	//TTS历史数据状态
	public static final String TTS_STATUS_P = "P"; //P-合成中
	public static final String TTS_STATUS_S = "S"; //S-完成
	public static final String TTS_STATUS_F = "F"; //F-失败
	public static final String TTS_STATUS_N = "N"; //N-查无数据
	
	//TTS失败类型
	public static final String TTS_ERROR_TYPE_P = "P"; //P-调用接口失败
	public static final String TTS_ERROR_TYPE_T = "T"; //T-TTS接口回调状态失败
	public static final String TTS_ERROR_TYPE_L = "L"; //P-TTS回调后本地处理失败

	//TTS接口查证状态（AI服务提供的TTS状态码）
	public static final String TTS_INTERFACE_UNDO = "0"; //未处理
	public static final String TTS_INTERFACE_DOING = "1"; //处理中
	public static final String TTS_INTERFACE_DONE = "2"; //处理完成
	public static final String TTS_INTERFACE_FAIL = "3"; //处理失败
	
	public static final String TTS_RSP_SUCCESS = "success"; //TTS合成返回成功状态
	
	public static final String RSP_CODE_SUCCESS = "0";	//外部请求返回的成功状态码
	
	public static final String LOCK_NAME_CFG = "LOCK_ROBOT_USER_AI_CFG_";	//资源锁-用户机器人数量变更
	public static final String LOCK_NAME_ASSIGN = "LOCK_ROBOT_USER_AI_ASSIGN_";	//资源锁-用户机器人分配
	
	/**
	 * 分配的机器人缓存
	 */
	public static final String ROBOT_ASSIGN_AI = "ROBOT_USER_AI_";
	
	/**
	 * 用户机器人资源
	 */
	public static final String ROBOT_USER_RESOURCE = "ROBOT_USER_RESOURCE";
	
	/**
	 * 话术模板资源
	 */
	public static final String ROBOT_TEMPLATE_RESOURCE = "ROBOT_TEMPLATE_RESOURCE";
}
