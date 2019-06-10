package com.guiji.clm.constant;

/** 
* @Description: 线路市场模块常量
* @Author: weiyunbo
* @date 2019年1月22日 下午9:38:45 
* @version V1.0  
*/
public class ClmConstants {
	
	//语音网关起始sip账号
	public static final int VOIP_ACCOUNT = 1000000;
	
	//语音网关sip账号步长
	public static final int VOIP_ACCOUNT_STEP = 1;
	
	//语音网关sip密码步长
	public static final int VOIP_PSD_STEP = 10;
	
	//变更路由规则锁
	public static final String LOCK_LINEMARKET_SAVERULE = "LOCK_LINEMARKET_SAVERULE_";
	
	//初始化voip设备锁
	public static final String LOCK_LINEMARKET_INIT_VOIP = "LOCK_LINEMARKET_INIT_VOIP";

	//line_info_map
	public static final String LINE_INFO_MAP="linemarket.line.info.map";
	
	public static final int UPDATE=1;

	public static final int DEL = 2;

	public static final int USER_DATA_AUTH_ME = 1;	//数据查询权限-本人
	public static final int USER_DATA_AUTH_ORG = 2;	//数据查询权限-本组织
	public static final int USER_DATA_AUTH_ORGTREE = 3;	//数据查询权限-本组织以及下级组织
}
