package com.guiji.botsentence.constant;

/**
 * 
 * @Description:常量
 * @author liyang  
 * @date 2018年8月16日  
 *
 */
public class Constant {

	private Constant() {
	}

	public static final String TEXT_ADD = "【新增】";

	public static final String TEXT_MODIFY = "【修改】";

	public static final String VOICE_TYPE_NORMAL = "00";

	public static final String VOICE_TYPE_RECOVER = "01";

	/**
	 * 流程，制作中
	 */
	public static final String APPROVE_MAEKING = "00";
	
	/**
	 * 流程，审核中
	 */
	public static final String APPROVE_CHECKING = "01";
	
	/**
	 * 流程，审核通过
	 */
	public static final String APPROVE_PASS = "02";
	
	/**
	 * 流程，审核不通过
	 */
	public static final String APPROVE_NOTPASS = "03";
	
	/**
	 * 流程，已上线
	 */
	public static final String APPROVE_ONLINE = "04";
	
	/**
	 * 流程，部署中
	 */
	public static final String DEPLOYING = "05";
	
	/**
	 * 流程，部署失败
	 */
	public static final String ERROR = "06";
	
	/**
	 * 录音类型，挽回话术
	 */
	public static final String VOLICE_TYPE_REFUSE = "01";
	
	/**
	 * 录音类型，一般录音 
	 */
	public static final String VOLICE_TYPE_NORMAL = "00";
	
	/**
	 * 录音类型，打断规则
	 */
	public static final String VOLICE_TYPE_INTERRUPT = "02";
	
	/**
	 * category类型==主流程（流程图显示部分）
	 */
	public static final String CATEGORY_TYPE_1 = "1";
	
	/**
	 * category类型==主流程（流程图不显示部分）
	 */
	public static final String CATEGORY_TYPE_3 = "3";
	
	/**
	 * 节点类型==开场白
	 */
	public static final String DOMAIN_TYPE_START = "start";
	
	/**
	 * 节点类型==一般节点
	 */
	public static final String DOMAIN_TYPE_PROCESS = "process";
	
	/**
	 * 节点类型==结束节点
	 */
	public static final String DOMAIN_TYPE_END = "end";
	
	/**
	 * 节点类型==转人工节点
	 */
	public static final String DOMAIN_TYPE_AGENT = "agent";
	
	/**
	 * 分支类型==一般
	 */
	public static final String BRANCH_TYPE_NORMAL = "01";
	
	/**
	 * 分支类型==未拒绝
	 */
	public static final String BRANCH_TYPE_POSITIVE = "02";
	
	public static final String DEFAULT_PASSWORD = "$2a$10$QDyc57bYtWpl/VFdyXB/1eYQ5MpV1ereSZHpQQL9yjV5bjUDm07mS";
	
	public static final String TTS_TASK_NEW = "00";
	
	public static final String TTS_TASK_ING = "01";
	
	public static final String TTS_TASK_FINISH = "02";
	
	/**
	 * 通话录音
	 */
	public static final String TTS_BUSI_TYPE_01 = "01";
	
	/**
	 * 备用话术
	 */
	public static final String TTS_BUSI_TYPE_02 = "02";
	
	/**
	 * 判断TTS变量的正则表达式
	 */
	public static final String TTS_REG_EX = "\\$[0-9]{4}";
	
	
	/**
	 * 备用话术对应的文案
	 */
	public static final String VOLICE_TYPE_BACKUP = "backup";
	
	/**
	 * TTS拆分出来的文案
	 */
	public static final String VOLICE_TYPE_TTS = "tts";
	
	
	/**
	 * 是否为变量-是
	 */
	public static final String IS_PARAM_TRUE = "01";
	
	
	/**
	 * 是否为变量-否
	 */
	public static final String IS_PARAM_FALSE = "02";
	
	
	/**
	 * 是否需要转人工
	 */
	public static final String NEED_AGENT_YES = "yes";
	
	public static final String TTS_ING = "01";
	
	public static final String TTS_FINISH = "02";

	
	//public static final String OFF_LINE = "on";
	
	//public static final String ALL_TTS = "on";
	
	public static final String CHANNEL_WECHAT = "wechat";
	public static final String CHANNEL_WEB = "web";
	
	public static final String OPERATOR_UPLOAD_VOLICE_BY_WECHAT = "uploadVoliceByWechat";
	public static final String OPERATOR_PHONE_TEST = "phoneTest";
	public static final String OPERATOR_PHONE_TEST_BY_WECHAT = "phoneTestByWechat";
	public static final String OPERATOR_PHONE_TEST_BY_WEB = "phoneTestByWeb";
	

	public static final String VARIABLE_FLAG_01 = "silence_wait";
	public static final String VARIABLE_FLAG_02 = "silence_as_empty";
	public static final String VARIABLE_FLAG_03 = "non_interruptable";
	public static final String VARIABLE_FLAG_04 = "interruption_config";
	public static final String VARIABLE_FLAG_05 = "interruptable_domain";
	public static final String VARIABLE_FLAG_06 = "global_interruptable_domains";
	public static final String VARIABLE_FLAG_07 = "ignoreButDomainsStart";
	public static final String VARIABLE_FLAG_08 = "ignoreUserSentenceStart";
	public static final String VARIABLE_FLAG_09 = "ignoreButNegativeStart";
	public static final String VARIABLE_FLAG_10 = "userDefineMatchOrder";
	public static final String VARIABLE_FLAG_11 = "notMatchLess4ToStart";
	public static final String VARIABLE_FLAG_12 = "notMatchToStart";
	public static final String VARIABLE_FLAG_13 = "noWordsToStart";
	public static final String VARIABLE_FLAG_14= "specialLimitStart";
	public static final String VARIABLE_FLAG_15= "nonInterruptableStart";
	
	public static final String SURVEY_INTENT_TYPE_DEFAULT = "00";
	
	public static final String SURVEY_INTENT_TYPE_DEFINE = "01";
	
	public static final String BRANCH_RULE_00 = "00";
	public static final String BRANCH_RULE_01 = "01";
	public static final String BRANCH_RULE_02 = "02";
	public static final String BRANCH_RULE_03 = "03";
	
	
	public static final String GRADE_PARAM_TYPE_1="触发某个主流程节点";
	public static final String GRADE_PARAM_TYPE_2="拒绝次数";
	public static final String GRADE_PARAM_TYPE_3="有效主流程对话轮数";
	public static final String GRADE_PARAM_TYPE_4="通话时长";
	public static final String GRADE_PARAM_TYPE_5="触发业务问答次数";
	public static final String GRADE_PARAM_TYPE_6="触发某个业务问答";
	
	public static final String CUSTOMER_DEFINE_KEYWORD = "自定义";
	
	
	/**
	 * 未部署
	 */
	public static final String TEST_STATE_UNDEPLOY = "00";
	/**
	 * 部署中
	 */
	public static final String TEST_STATE_DEPLOYING = "01";
	/**
	 * 已部署
	 */
	public static final String TEST_STATE_ONLINE = "02";
	
	
	public static final String APPROVE_TYPE = "approve";
	
	/**
	 * 分享给所有人
	 */
	public static final String SHARE_TYPE_00 = "00";
	
	/**
	 * 分享给指定用户
	 */
	public static final String SHARE_TYPE_01 = "01";
}

