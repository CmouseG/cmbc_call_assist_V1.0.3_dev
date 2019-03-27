package com.guiji.auth.constants;

/** 
* @ClassName: AuthConstants 
* @Description: auth用户模块常量类
* @auth weiyunbo
* @date 2019年3月13日 下午9:11:23 
* @version V1.0  
*/
public class AuthConstants {

	/**
	 * 系统最顶层组织代码
	 */
	public static final String ROOT_ORG_CODE = "1";
	/**
	 * 超管
	 */
	public static final String ROOT_ROLE_ADMIN = "1";
	
	/**
	 * 人工坐席权限
	 * 拥有人工坐席权限的人在转人工时可以选到，现在把这个挂在菜单按钮下作为一个操作权限当角色使用，因为角色不再固定
	 */
	public static final int MENU_AGENT_MEMBER = 50;
}
