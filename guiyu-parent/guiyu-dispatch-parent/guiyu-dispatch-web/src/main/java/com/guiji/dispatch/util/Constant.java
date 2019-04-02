package com.guiji.dispatch.util;

public class Constant {

	// 0未计划1计划中2计划完成3暂停计划4停止计划
	public static final Integer STATUSPLAN_0 = 0;
	public static final Integer STATUSPLAN_1 = 1;
	public static final Integer STATUSPLAN_2 = 2;
	public static final Integer STATUSPLAN_3 = 3;
	public static final Integer STATUSPLAN_4 = 4;
	public static final Integer STATUSPLAN_5_REDIS = 5;
	// 同步呼叫中心状态	0未同步1已同步
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

	//洗号码标识	0-未准备 1-准备中 2-已完成
	public static final String IS_FLAG_0 = "0";
	public static final String IS_FLAG_1 = "1";
	public static final String IS_FLAG_2 = "2";

	public static final Integer THIRD_INTERFACE_RETRYTIMES = 9;

	// 任务状态
	public static final Integer MODULAR_STATUS_START = 0;
	public static final Integer MODULAR_STATUS_END = 1;
	public static final Integer MODULAR_STATUS_ERROR = 2;

	// 0代表任务模块
	public static final Integer MODULAR_NAME_DISPATCH = 0;

	public static final Integer BATCH_STATUS_SHOW = 0;
	public static final Integer BATCH_STATUS_NO = 1;

	public static final Integer INIT = 1;
	public static final Integer CHECKRESOURCE = 2;
	public static final Integer PUSHHANDLER = 3;

	public static final Integer NOCALLBACK = 0;
	public static final Integer CALLBACKED = 1;
	
	public static final String TYPE_ALL = "1";
	public static final String TYPE_NOALL ="0";

	
	public static final Integer THIRDAPISUCCESS = 1;
	public static final Integer THIRDAPIFAILURE =0;

	
	public static final Integer IMPORT_DATA_TYPE_PAGE=0;
	public static final Integer IMPORT_DATA_TYPE_API=1;
	
	
	public static final Integer BLACKSTATUSOK=0;
	public static final Integer BLACKSTATUSNO=1;
	
	
	public static final String FILE_SHOW = "0";
	public static final String FILE_NO ="1";



	public static final Integer BLACK_LIST_IMPORT_UNIDENTIFIED=1;//未知号码
	public static final Integer BLACK_LIST_IMPORT_DUPLICATE=2;	//已包含
	public static final Integer BLACK_LIST_IMPORT_INDB=3;		//号码黑名单已存在
	public static final Integer BLACK_LIST_IMPORT_WRONGFUL=4;//不合法
	public static final Integer BLACK_LIST_IMPORT_LENGTH=5;//长度不正确
}
