package com.guiji.dispatch.util;

public class Constant {

	// 0未计划1计划中2计划完成3暂停计划4停止计划
	public static final Integer STATUSPLAN_0 = 0;
	public static final Integer STATUSPLAN_1 = 1;
	public static final Integer STATUSPLAN_2 = 2;
	public static final Integer STATUSPLAN_3 = 3;
	public static final Integer STATUSPLAN_4 = 4;
	// 0未同步1已同步
	public static final Integer STATUS_SYNC_0 = 0;
	public static final Integer STATUS_SYNC_1 = 1;

	public static final Integer ISCALL_0 = 0;
	public static final Integer ISCALL_1 = 1;

	public static final Integer STATUSNOTIFY_0 = 0;
	public static final Integer STATUSNOTIFY_1 = 1;
	public static final Integer STATUSNOTIFY_2 = 2;

	// '通知状态1等待2失败3成功',
	public static final Integer STATUS_NOTIFY_0 = 1;
	public static final Integer STATUS_NOTIFY_1 = 2;
	public static final Integer STATUS_NOTIFY_2 = 3;

	// 重播类型 0一般任务 1 重播任务
	public static final Integer REPLAY_TYPE_0 = 0;
	public static final Integer REPLAY_TYPE_1 = 1;

	// 0 未删除 1 删除
	public static final Integer IS_DEL_0 = 0;
	public static final Integer IS_DEL_1 = 1;

	public static final Integer IS_TTS_0 = 0;
	public static final Integer IS_TTS_1 = 1;

	// 0清除 1 不清除第二天继续打
	public static final Integer IS_CLEAN_0 = 0;
	public static final Integer IS_CLEAN_1 = 1;

	public static final String IS_FLAG_0 = "0";
	public static final String IS_FLAG_1 = "1";
	public static final String IS_FLAG_2 = "2";

	public static final Integer THIRD_INTERFACE_RETRYTIMES = 9;

	// 0开始 1结束 2异常
	public static final Integer MODULAR_STATUS_0 = 0;
	public static final Integer MODULAR_STATUS_1 = 1;
	public static final Integer MODULAR_STATUS_2 = 2;

	//0代表人物模块
	public static final Integer MODULAR_NAME = 0;

}
