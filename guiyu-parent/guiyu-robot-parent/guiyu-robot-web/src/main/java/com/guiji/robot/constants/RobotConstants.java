package com.guiji.robot.constants;

import java.util.Arrays;
import java.util.List;

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
	public static final int USER_CFG_STATUS_S = 1; //1-正常
	public static final int USER_CFG_STATUS_V = 0; //0-失效
	
	//用户账号变更历史-操作
	public static final int HANDLE_TYPE_A = 1; //新增
	public static final int HANDLE_TYPE_U = 2; //更新
	public static final int HANDLE_TYPE_D = 0; //删除
	
	//机器人状态
	public static final String AI_STATUS_F = "F"; //F-空闲
	public static final String AI_STATUS_B = "B"; //B-忙
	public static final String AI_STATUS_P = "P"; //P-暂停分配
	
	//TTS历史数据状态
	public static final int TTS_STATUS_P = 2; //P-合成中
	public static final int TTS_STATUS_S = 1; //S-完成
	public static final int TTS_STATUS_F = 0; //F-失败
	public static final int TTS_STATUS_N = 9; //N-查无数据
	
	//TTS失败类型
	public static final int TTS_ERROR_TYPE_P = 1; //P-调用接口失败
	public static final int TTS_ERROR_TYPE_T = 2; //T-TTS接口回调状态失败
	public static final int TTS_ERROR_TYPE_L = 3; //P-TTS回调后本地处理失败

	//TTS接口查证状态（AI服务提供的TTS状态码）
	public static final int TTS_INTERFACE_UNDO = 0; //未处理
	public static final int TTS_INTERFACE_DOING = 1; //处理中
	public static final int TTS_INTERFACE_DONE = 2; //处理完成
	public static final int TTS_INTERFACE_FAIL = 3; //处理失败
	
	public static final String TTS_RSP_SUCCESS = "success"; //TTS合成返回成功状态
	
	public static final String RSP_CODE_SUCCESS = "0";	//外部请求返回的成功状态码
	
	public static final String LOCK_NAME_CFG = "LOCK_ROBOT_USER_AI_CFG_";	//资源锁-用户机器人数量变更
	public static final String LOCK_NAME_ASSIGN = "LOCK_ROBOT_USER_AI_ASSIGN_";	//资源锁-用户机器人分配
	public static final String LOCK_ROBOT_AIPOOL_INIT = "LOCK_ROBOT_AIPOOL_INIT";	//资源锁-机器人池初始化
	public static final String LOCK_ROBOT_AIPOOL_ASSIGN = "LOCK_ROBOT_AIPOOL_ASSIGN";	//资源锁-机器人池分配锁
	
	/**
	 * 通话状态
	 */
	public static final int CALLINT_STATUS_I = 1; //I-通话中
	public static final int CALLING_STATUS_S = 2; //S-通话完成
	
	/**
	 * 播音状态
	 */
	public static final String CALL_STATUS_OVER = "0"; //播音结束
	public static final String CALL_STATUS_ING = "1"; //播音中
	public static final String CALL_STATUS_BEGIN = "999"; //开场白
	
	/**
	 * 返回告诉软电话的处理动作
	 */
	public static final String HELLO_STATUS_PLAY = "play"; //播放
	public static final String HELLO_STATUS_WAIT = "wait"; //继续不做动作
	
	/**
	 * AI资源池
	 */
	public static final String ROBOT_POOL_AI = "ROBOT_POOL_AI";
	
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
	
	/**
	 * 用户AI通话信息流资源
	 */
	public static final String ROBOT_SENTENCE_RESOURCE = "ROBOT_SENTENCE_";
	
	
	//单字白名单，这些单字不忽略
	public static final List<String> filter_text = Arrays.asList("滚", "交", "叫", "教", "较", "角", "带", "贷", "代", "好", "哦", "没", "行", "是", "想", "嗯", "不", "讲", "有");
	//黑名单
	public static final List<String> black_list = Arrays.asList("喂", "你好", "您好");
	//白名单
	public static final List<String> white_list = Arrays.asList("恩", "嗯", "好", "好的", "是", "是的", "知道", "明白");

}
