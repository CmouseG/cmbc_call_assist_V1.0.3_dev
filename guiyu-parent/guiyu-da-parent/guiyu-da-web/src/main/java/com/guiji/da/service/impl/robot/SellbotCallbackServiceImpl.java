package com.guiji.da.service.impl.robot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.da.dao.entity.RobotCallHis;
import com.guiji.da.exception.DaException;
import com.guiji.da.service.robot.IRobotCallHisService;
import com.guiji.da.service.robot.ISellbotCallbackService;
import com.guiji.da.service.vo.RobotCallProcessStatCache;
import com.guiji.da.service.vo.SellbotCallInfo;
import com.guiji.da.service.vo.SellbotCallSentence;
import com.guiji.da.util.UnicodeUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: SellbotCallbackServiceImpl 
* @Description: 接收Sellbot通话记录回调服务
* @date 2018年12月6日 下午2:31:18 
* @version V1.0  
*/
@Service
public class SellbotCallbackServiceImpl implements ISellbotCallbackService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IRobotCallHisService iRobotCallHisService;
	@Autowired
	RobotNewTransService robotNewTransService;
	@Autowired
	RobotCacheService robotCacheService;
	
	
	/**
	 * 接收Sellbot每通电话的回调数据（数据主要包括来回的对话）
	 * 1、将回调的JSON转为对象
	 * 2、查询通话信息，并将JSON数据保存到通话信息中，以供以后统计分析使用
	 * 3、本条数据分析
	 * 4、更新到redis中
	 * @param sellbotJson
	 */
	public void receiveSellbotCallback(String sellbotJson) {
		//1、先将JSON转为对象
		if(StrUtils.isNotEmpty(sellbotJson)) {
			//sellbot报送的报文中文经过了unicode转码,此处要重新转回来
			sellbotJson = UnicodeUtil.unicodeTocn(sellbotJson);
			logger.info("Sellbot通话回调："+sellbotJson);
			//JSON转对象
			SellbotCallInfo sellbotCallInfo = JsonUtils.json2Bean(sellbotJson, SellbotCallInfo.class);
			//2、查询并将回调信息更新到通话记录中
			RobotCallHis rRobotCallHis = this.upgradeRobotCallback(sellbotJson,sellbotCallInfo);
			//3、分析重新组装回调的这条通话数据
			if(rRobotCallHis != null) {
				Map<String,RobotCallProcessStatCache> callMap = this.dataAnalysis(rRobotCallHis, sellbotCallInfo);
				//4、将分析的这条通话数据合并到当天的缓存中
				this.upgradeTodayRobotStatCache(rRobotCallHis.getUserId(), rRobotCallHis.getTemplateId(), callMap);
			}
		}
	}
	
	
	/**
	 * 查询并更新到通话记录中
	 * @param sellbotCallInfo
	 * @param sellbotCallInfo 回调数据
	 * @return
	 */
	private RobotCallHis upgradeRobotCallback(String sellbotJson,SellbotCallInfo sellbotCallInfo) {
		if(StrUtils.isNotEmpty(sellbotJson)) {
			if(sellbotCallInfo != null) {
				//必输校验
				String seqId = sellbotCallInfo.getSeqid();	//会话ID
				if(StrUtils.isEmpty(seqId)) {
					throw new DaException("必输字段seqId不能为空!");
				}
				//根据会话id查询通话记录
				RobotCallHis robotCallHis = iRobotCallHisService.queryRobotCallBySeqId(seqId);
				if(robotCallHis != null) {
					robotCallHis.setSellbotCallbackJson(sellbotJson);
					//保存回调数据
					robotNewTransService.recordRobotCallHis(robotCallHis);
					return robotCallHis;
				}else {
					throw new DaException("根据会话Id:"+seqId+"查询不到通话数据");
				}
			}
		}else {
			logger.error("sellbot返回的json报文为空!");
		}
		return null;
	}
	
	
	/**
	 * 一条数据的分析结果(以domain+aiAnswer为维度进行统计)
	 * @param robotCallHis
	 * @return
	 */
	private Map<String,RobotCallProcessStatCache> dataAnalysis(RobotCallHis robotCallHis,SellbotCallInfo sellbotCallInfo){
		if(robotCallHis != null) {
			Map<String,RobotCallProcessStatCache> map = new HashMap<String,RobotCallProcessStatCache>();
			//对话列表
			List<SellbotCallSentence> sellbotCallSentenceList = sellbotCallInfo.getData();
			if(sellbotCallSentenceList!=null && !sellbotCallSentenceList.isEmpty()){
				String statDate = DateUtil.getCurrentymd();
				//开始逐条分析一通电话的对话记录
				for(SellbotCallSentence sentence : sellbotCallSentenceList){
					String currentDomain = sentence.getCurrent_domain();	//当前域
					String domainType = sentence.getDomain_type();	//域类型
					String aiAnswer = sentence.getAi_answer();	//ai说的话
					String isRefused = sentence.getIs_refused();	//拒绝类型
					String hangupType = sentence.getHangup_type();	//挂断类型
					String matchType = sentence.getMatch_type();	//匹配类型
					if(StrUtils.isNotEmpty(currentDomain) && StrUtils.isNotEmpty(aiAnswer)) {
						//一通电话的分析数据
						String key = currentDomain+"|"+aiAnswer;
						RobotCallProcessStatCache robotCallProcessStat = map.get(key);
						if(robotCallProcessStat == null) {
							//初始化条
							robotCallProcessStat = new RobotCallProcessStatCache();
							robotCallProcessStat.setCurrentDomain(currentDomain);
							robotCallProcessStat.setDomainType(domainType);
							robotCallProcessStat.setAiAnswer(aiAnswer);
							robotCallProcessStat.setStatDate(statDate);
							robotCallProcessStat.setUserId(robotCallHis.getUserId());	//	用户id
							robotCallProcessStat.setTemplateId(robotCallHis.getTemplateId());	//模板id
						}
						//设置拒绝统计
						robotCallProcessStat.setRefusedStatMap(this.addNum(robotCallProcessStat.getRefusedStatMap(), isRefused));
						//设置挂断类型统计
						robotCallProcessStat.setHangupStatMap(this.addNum(robotCallProcessStat.getHangupStatMap(), hangupType));
						//设置匹配类型统计
						robotCallProcessStat.setMatchStatMap(this.addNum(robotCallProcessStat.getMatchStatMap(), matchType));
						//total+1
						robotCallProcessStat.setTotalStat(robotCallProcessStat.getTotalStat()+1);
						map.put(key, robotCallProcessStat);
					}
				}
			}
			return map;
		}
		return null;
	}
	
	
	/**
	 * 将用户缓存更新到内存中
	 * @param userId
	 * @param templateId
	 * @param map
	 */
	public void upgradeTodayRobotStatCache(String userId,String templateId,Map<String,RobotCallProcessStatCache> sentenceMap){
		if(StrUtils.isNotEmpty(userId) && StrUtils.isNotEmpty(templateId) && sentenceMap!=null && !sentenceMap.isEmpty()){
			//获取到缓存中该用户某个模板的已有话术统计
			Map<String,RobotCallProcessStatCache> userTemplateCallStatMap = robotCacheService.queryUserCallStatByUserAndTemplate(userId, templateId);
			if(userTemplateCallStatMap == null || userTemplateCallStatMap.isEmpty()) {
				//已经存在的为空，直接提交到内存
				robotCacheService.cacheRobotCallProcessStat(userId, templateId, sentenceMap);
			}else {
				//内存中已经存在，那么按话术将数据合并
				for (Map.Entry<String,RobotCallProcessStatCache> callStatEntry : sentenceMap.entrySet()) {
					String sentence = callStatEntry.getKey();
					RobotCallProcessStatCache newRobotCallProcessStatCache = callStatEntry.getValue();
					//根据话术ID查询内存中的
					String key = newRobotCallProcessStatCache.getCurrentDomain()+"|"+newRobotCallProcessStatCache.getAiAnswer();
					RobotCallProcessStatCache existRobotCallProcessStat = userTemplateCallStatMap.get(key);
					if(existRobotCallProcessStat == null) {
						//直接放入内存中的map
						userTemplateCallStatMap.put(sentence, newRobotCallProcessStatCache);
					}else {
						//数据合并
						existRobotCallProcessStat.setTotalStat(existRobotCallProcessStat.getTotalStat()+newRobotCallProcessStatCache.getTotalStat()); //总数合并
						//拒绝统计合并
						existRobotCallProcessStat.setRefusedStatMap(this.addNum(existRobotCallProcessStat.getRefusedStatMap(), newRobotCallProcessStatCache.getRefusedStatMap()));
						//挂断统计合并
						existRobotCallProcessStat.setHangupStatMap(this.addNum(existRobotCallProcessStat.getHangupStatMap(), newRobotCallProcessStatCache.getHangupStatMap()));
						//匹配统计合并
						existRobotCallProcessStat.setMatchStatMap(this.addNum(existRobotCallProcessStat.getMatchStatMap(), newRobotCallProcessStatCache.getMatchStatMap()));
					}
				}
				//更新缓存中的数据
				robotCacheService.cacheRobotCallProcessStat(userId, templateId, userTemplateCallStatMap);
			}
		}
	}
	
	
	
	/**
	 * 将Map中某个key对应的数据统计+1
	 * @param map
	 * @param key
	 */
	private Map<String,Integer> addNum(Map<String,Integer> map,String key){
		if(StrUtils.isNotEmpty(key)) {
			if(map == null) {
				map = new HashMap<String,Integer>();
			}
			Integer num = map.get(key);
			map.put(key, (num==null?0:num)+1);
		}
		return map;
	}
	
	/**
	 * 2个Map中数据合并
	 * @param existMap
	 * @param newMap
	 * @return
	 */
	private Map<String,Integer> addNum(Map<String,Integer> existMap,Map<String,Integer> newMap){
		if(existMap == null) {
			if(newMap != null) {
				existMap = newMap;
			}else {
				existMap = new HashMap<String,Integer>();
			}
			return existMap;
		}
		if(newMap !=null && !newMap.isEmpty()) {
			for (Map.Entry<String,Integer> newMapEntry : newMap.entrySet()) {
				String key = newMapEntry.getKey();
				Integer existNum = existMap.get(key);
				Integer newNum = existMap.get(key);
				existMap.put(key, (existNum==null?0:existNum)+(newNum==null?0:newNum));
			}
		}
		return existMap;
	}
	
}
