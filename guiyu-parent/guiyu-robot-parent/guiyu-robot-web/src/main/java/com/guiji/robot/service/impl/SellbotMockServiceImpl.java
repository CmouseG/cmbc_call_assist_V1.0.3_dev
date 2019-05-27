package com.guiji.robot.service.impl;

import com.guiji.robot.model.HangupRes;
import com.guiji.robot.service.ISellbotService;
import com.guiji.robot.service.vo.*;
import com.guiji.utils.LocalCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** 
* @ClassName: SellbotMockServiceImpl 
* @Description: sellbot挡板
* @date 2019年3月3日 下午2:45:50 
* @version V1.0  
*/
@Service
public class SellbotMockServiceImpl implements ISellbotService{

	/** logger */
	private final static Logger LOGGER = LoggerFactory.getLogger(SellbotMockServiceImpl.class);

	//机器人-开场白
	private static final String[] prologueAnswers = {
			"您好，我是成都爱巧匠互联网智能装修公司的小王，来电是想邀请您参加元旦期间，我们公司在南充举行的一场盛大的智能家装节，活动中既可以现场体验智能装修装出来的智慧家庭的效果，还有全屋家具14件套赠送，这边简单给您介绍一下您看行吗?",
			"好，我是常春藤医疗美容客服，我们4月下旬举办周年庆，全场美容项目1折起。上百个项目如脱毛、瘦脸、玻尿酸、线雕低于3折，补水全线项目9块9，苏州最低，买贵就退！请问您有兴趣了解吗",
			"您好，我这里是兴业银行的，诚邀您办理一张我行的白金信用卡，额度最高10万，请问您是否需要办理一张呢？"
	};
	//机器人-hello 回答
	private static final String[] helloAnswers = {
			"我们公司地址在北京。",
			"这样吧，稍后给您发一条官方申请短信，打开链接填写一下信息就行了，谢谢接听，再见！",
			"这您可以放心的，我们提供的是银行官方的申请链接，填写申请后，不管成功与否都会收到银行的通知短信。您收到卡片之后本人持身份证原件、信用卡、开卡函及申请时的电话到就近网点核准身份后就能立即使用了。",
			"这样吧，稍后我给您发一条申请短信，您有需要的话可以直接点击链接进行申请，好吧？",
			"我是信用卡申请机构的客服人员",
			"我们是兴业银行授权的推广机构",
			"你问的这个项目我们搞活动超级便宜的，我们全场最低1折起，具体要根据你自身情况和医生会诊后的建议，比如是做单次还是做疗程，是做单项还是做叠加，我都会把你关心的问题反馈给主管，给你申请最便宜的价格。到时候她会给您报价",
			"稍等下，信号不好，您说什么",
			"价格是根据您家的面积，装修风格和材质，以及我们所包含的项目多少来决定的，活动期间可以享受总经理亲自放价优惠，已经有好多业主报名参加了，装修就跟买期房一样，越早越便宜啊",
			"谢谢您的接听，就不多打扰你了",
			"不好意思，可能信号太差了，我这边没有声音，下次再联系吧，拜拜",
			"您看我这边也是新来的，您的问题一时也无法准确答复您。稍后让我们专业的客户经理给您来电为您解答吧。那今天咱们就先这样吧，祝您生活愉快",
			"您想啊，多一张信用卡也是多一笔备用金在手边，另外，每家银行的活动、服务也会各有不同，而且现在还款又无需出门，不用担心手续问题的，您真的可以考虑办一张的",
			"是这样的，我这里是兴业银行的，诚邀您办理一张我行的白金信用卡，稍候我们会发送一条短信，您点击短信内我行官方链接，花1分钟填写一下，就可以申请了，您看可以吗？",
			"我们这张白金卡不仅免年费，还有很多活动，譬如淘宝购物给积分，境内外机场免费接送服务等，通过率很高的，只要通过短信做个线上申请就可以了，您看好吗？",
			"哦，那等您方便的时候我们再联系，再见！",
			"您可以再考虑一下哦。毕竟我们周年庆期间，全场美容项目1折起，我们是正规医美机构，价格苏州最低，比美容院还便宜，比如全线补水项目才9块9，全年仅4天。错过再等一年",
			"这样吧，我建议您可以详细了解下再决定，要不后续让我们专业的咨询医生给您回复，好吧",
			"好可惜啊，如果真的来不了，可以让你朋友帮忙预定。实在不行你可以预交100定金转给我们财务保留活动名额",
			"这个到时让我们专业的咨询医生跟您沟通一下吧",
			"喂，喂，信号不好，您刚说什么啊？",
			"好意思，您刚说什么？",
			"不好意思，可能信号太差了，我这边没有声音，下次再联系吧，拜拜！",
			"好的，感谢您的耐心聆听，后续会有专业的咨询医生与您联系，这边就先不打扰您了。祝您生活愉快，再见",
			"您好，我是中国人寿财险合肥公司的续保专员。系统显示，您去年投保的车险快要到期了，如果今年您的保险项目不需要调整，我们近期将安排工作人员帮您试算下今年的保费，便于您及时续保",
			"是这样的，我是中国人寿财险合肥公司的续保专员,系统显示，您去年投保的车险快要到期了，如果今年您的保险项目不需要调整，我们会安排工作人员帮您试算下今年的保费，便于您及时续保。你看可以吗？",
			"我们中国人寿目前也是世界的双500强公司,公司服务是有保障、值得信赖的，电话投保服务不但价格优惠，还可以免费送单上门。如发生保险事故，我们双服务热线可以全天候24小时办理报案等售后业务，保证让客户享受方便、快捷的车险理赔服务。",
			"您看下刚才给您介绍的服务，如果您觉得可以的话，我就帮您核对一下资料，直接帮您办理续保，保单就可以直接给您送过来了，很方便的，可以吧？",
			"好的，耽误您一分钟，为您做个报价，报价您满意的话，我们可以直接为您送单上门，您看今年是想要保的全面一些还是基本一些的？",
			"您是买过了，还是什么原因呢？方便告诉我，我来做个登记，方便明年为您提供更好的服务呢。*",
			"您在我们公司有办理过贷款,给您打电话是提醒您按时还款。如果您有任何疑问，可以联系01053779068"
	};
	//要播放的录音文件
	private static final String[] wavFiles = {
			"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","28","29","30"};
 	//意图级别
	private static final String[] intents = {"A","B","C","D"};
	
	/**
	 * sellbot初始化接口,每通电话前需要调用下初始化操作。
	 * @param sellbotRestoreReq
	 * @return
	 */
	public String restore(AiBaseInfo ai,SellbotRestoreReq sellbotRestoreReq) {
		this.mockCallTime();
		this.setCallCache(sellbotRestoreReq.getSeqid());
		return "{\r\n" + 
				"    \"UserInfo\": \"开场白\",\r\n" + 
				"    \"accurate_intent\": \""+getRandomArray(intents)+"\",\r\n" + 
				"    \"answer\": \""+getRandomArray(prologueAnswers)+"\",\r\n" + 
				"    \"answered_branch\": \"enter\",\r\n" + 
				"    \"answered_domain\": \"开场白\",\r\n" + 
				"    \"app_log\": \"/home/log_sellbot/app/20190303_15014/lllll_app.log\",\r\n" + 
				"    \"app_port\": 15014,\r\n" + 
				"    \"cfg_ver\": \"\",\r\n" + 
				"    \"dtmfend\": \"\",\r\n" + 
				"    \"dtmfflag\": \"false\",\r\n" + 
				"    \"dtmflen\": \"1\",\r\n" + 
				"    \"dtmfresult\": \"-1\",\r\n" + 
				"    \"dtmftimeout\": \"10\",\r\n" + 
				"    \"end\": 0,\r\n" + 
				"    \"interrupt_min_interval\": null,\r\n" + 
				"    \"reason\": \"得分 <= 9.0 or 得分 < 0.0，实际得分: {得分}，或者接通即挂断\",\r\n" + 
				"    \"sentence\": \"\",\r\n" + 
				"    \"seqid\": \""+sellbotRestoreReq.getSeqid()+"\",\r\n" + 
				"    \"silence_wait_secs\": 0,\r\n" + 
				"    \"state\": \"开场白\",\r\n" + 
				"    \"user_attentions\": \"开场白\",\r\n" + 
				"    \"wav_filename\": \""+getRandomArray(wavFiles)+"\"\r\n" +
				"}"; 
	}
	
	
	/**
	 * sellbot客户语句响应服务
	 * @param sellbotSayhelloReq
	 * @return
	 */
	public String sayhello(AiBaseInfo ai,SellbotSayhelloReq sellbotSayhelloReq) {
		this.mockCallTime();
		String endFlag = "0";	//默认不结束
		int talkNum = this.getCallCache(sellbotSayhelloReq.getSeqId());	//对话轮数
		switch(talkNum) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				//3轮对话50%概率挂断
				endFlag = this.getRandomArray(new String[]{"0","1"});
				break;
			case 4:
				//3轮对话60%概率挂断
				endFlag = this.getRandomArray(new String[]{"0","0","0","0","1","1","1","1","1","1"});
				break;
			case 5:
				//5轮对话100%概率挂断
				endFlag = "1";
				break;
			default:
				endFlag = "1";
		}
		this.setCallCache(sellbotSayhelloReq.getSeqId());
		return "{\r\n" + 
				"    \"UserInfo\": \"\",\r\n" + 
				"    \"accurate_intent\": \""+getRandomArray(intents)+"\",\r\n" + 
				"    \"answer\": \""+getRandomArray(helloAnswers)+"\",\r\n" + 
				"    \"answered_branch\": \"special_03.responses\",\r\n" + 
				"    \"answered_domain\": \"一般问题\",\r\n" + 
				"    \"cfg_ver\": \"\",\r\n" + 
				"    \"dtmfend\": \"\",\r\n" + 
				"    \"dtmfflag\": \"false\",\r\n" + 
				"    \"dtmflen\": \"1\",\r\n" + 
				"    \"dtmfresult\": \"-1\",\r\n" + 
				"    \"dtmftimeout\": \"10\",\r\n" + 
				"    \"end\": "+endFlag+",\r\n" + 
				"    \"intent\": \""+getRandomArray(intents)+"\",\r\n" + 
				"    \"intention_confidence\": \"\",\r\n" + 
				"    \"keywords\": \"价格\",\r\n" + 
				"    \"match_method\": 0,\r\n" + 
				"    \"match_nothing\": 0,\r\n" + 
				"    \"match_type\": 2,\r\n" + 
				"    \"reason\": \"得分 >= 50.0，实际得分: 788\",\r\n" + 
				"    \"sentence\": \"价格\",\r\n" + 
				"    \"seqid\": \""+sellbotSayhelloReq.getSeqId()+"\",\r\n" + 
				"    \"state\": \"一般问题\",\r\n" + 
				"    \"used_time_ms\": 33.659,\r\n" + 
				"    \"user_attentions\": \"[('开场白', 1), ('一般问题', 1)]\",\r\n" + 
				"    \"wav_filename\": \""+getRandomArray(wavFiles)+"\",\r\n" + 
				"    \"word_segment_result\": \"['价格']\"\r\n" + 
				"}";
	}
	
	
	/**
	 * sellbot关键字查询匹配接口请求信息
	 * @param sellbotMatchReq
	 * @return
	 */
	@Override
	public String match(AiBaseInfo ai,SellbotMatchReq sellbotMatchReq) {
		this.mockCallTime();
		return "{\r\n" + 
				"    \"match_keywords\":\"\",\r\n" + 
				"    \"matched\": 1,\r\n" + 
				"    \"sentence\": \""+sellbotMatchReq.getSentence()+"\"\r\n" + 
				"}";
	}

    @Override
    public HangupRes clean(AiInuseCache aiInuseCache, EndReq endReq) {

	    HangupRes res = new HangupRes();

	    res.setSeqid(endReq.getSeqId());
	    res.setAccurate_intent("A");

	    return res;

    }

//    /**
//	 * 电话挂断后做数据清理（目前只有飞龙需要，sellbot不需要，为接口统一需要统一封装下）
//	 * @param flHelloReq
//	 * @return
//	 */
//	@Override
//	public void clean(FlHelloReq flHelloReq) {
//		//sellbot 在restore清理
//	}
	
	/**
	 * 从数组从随机获取一个元素
	 * @param array
	 * @return
	 */
	private String getRandomArray(String[] array) {
		int min = 0; //最小
		int max = array.length-1;	//最大
		int average = (int)(min+Math.random()*(max-min+1));
		return array[average];
	}
	
	/**
	 * 模拟调用sellbot的响应时间
	 */
	private void mockCallTime() {
		int min = 200; //最小响应时间200ms
		int max = 800;	//最大响应时间800ms
		int average = (int)(min+Math.random()*(max-min+1));

		try {
			Thread.sleep(average);
		} catch (InterruptedException e) {
			LOGGER.error("模拟调用sellbot的响应时间异常： {}", e.getMessage());
		}
	}
	/**
	 * 获取缓存
	 * 某通电话的对话轮数
	 * @param seqId
	 */
	private int getCallCache(String seqId) {
		String key = "MOCK_"+seqId;
		if(LocalCacheUtil.get(key)!=null) {
			return (int) LocalCacheUtil.get(key);
		}else {
			//电话-对话轮数  缓存5分钟
			LocalCacheUtil.set(key, 1, 5*60*1000);
		}
		return 1;
	}
	/**
	 * 设置缓存
	 * @param seqId
	 */
	private void setCallCache(String seqId) {
		String key = "MOCK_"+seqId;
		if(LocalCacheUtil.get(key)!=null) {
			int num = (int) LocalCacheUtil.get(key);
			LocalCacheUtil.set(key, num+1, 5*60*1000);
		}else {
			//电话-对话轮数  缓存5分钟
			LocalCacheUtil.set(key, 1, 5*60*1000);
		}
	}
	
}
