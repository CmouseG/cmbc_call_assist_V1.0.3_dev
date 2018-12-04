package ai.guiji.botsentence.constant;

/**
 * 
 * @Description:常量
 * @author liyang  
 * @date 2018年8月16日  
 *
 */
public class Constant {

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
}
