package com.guiji.dispatch.model;

public class Constant {

	// 任务状态
	public static final Integer MODULAR_STATUS_START = 0;
	public static final Integer MODULAR_STATUS_END = 1;
	public static final Integer MODULAR_STATUS_ERROR = 2;

	// 模块名称
	public static final Integer MODULAR_DISPATCH = 0;
	public static final Integer MODULAR_CALLCENTER = 1;
	public static final Integer MODULAR_ROBOT = 2;
	public static final Integer MODULAR_TTS = 3;

	public static final String STATUSPLAN_PLANING = "1";
	public static final String STATUSPLAN_SUCCESS = "2";
	public static final String STATUSPLAN_PAUSE = "3";
	public static final String STATUSPLAN_STOP ="4";
	
	public static final String TYPE_ALL = "1";
	public static final String TYPE_NOALL ="0";
}
