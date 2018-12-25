package com.guiji.da.service.impl.robot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.da.dao.RobotCallProcessStatMapper;
import com.guiji.da.dao.entity.RobotCallProcessStat;
import com.guiji.da.dao.entity.RobotCallProcessStatExample;
import com.guiji.da.dao.entity.RobotCallProcessStatExample.Criteria;
import com.guiji.da.service.job.RobotStatJobTimer;
import com.guiji.da.service.robot.IRobotCallProcessStatService;
import com.guiji.da.service.vo.RobotCallProcessStatCache;
import com.guiji.da.service.vo.RobotCallProcessStatQueryCondition;
import com.guiji.da.util.GroupUtils;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: RobotCallProcessStatServiceImpl 
* @Description: 机器人统计分析
* @date 2018年12月6日 下午3:10:02 
* @version V1.0  
*/
@Service
public class RobotCallProcessStatServiceImpl implements IRobotCallProcessStatService{
	@Autowired
	RobotCallProcessStatMapper robotCallProcessStatMapper; 
	@Autowired
	RobotCacheService robotCacheService;
	@Autowired
	RobotStatJobTimer robotStatJobTimer;
	
	

	/**
	 * 保存或者更新一条统计分析数据
	 * 同时记录历史
	 * @param ttsWavHis
	 * @return
	 */
	@Transactional
	@Override
	public RobotCallProcessStat saveOrUpdate(RobotCallProcessStat robotCallProcessStat) {
		if(robotCallProcessStat != null) {
			if(StrUtils.isEmpty(robotCallProcessStat.getId())) {
				//如果主键为空，那么新增一条信息
				robotCallProcessStat.setCrtTime(new Date());
				robotCallProcessStatMapper.insert(robotCallProcessStat);	//创建时间
			}else {
				//主键不为空，更新信息
				robotCallProcessStatMapper.updateByPrimaryKey(robotCallProcessStat);
			}
		}
		return robotCallProcessStat;
	}
	
	
	/**
	 * 根据条件查询统计分析数据
	 * @param condition
	 * @return
	 */
	@Override
	public List<RobotCallProcessStatCache> queryRobotCallProcessStatByCondition(RobotCallProcessStatQueryCondition condition) {
		List<RobotCallProcessStatCache> totalList = new ArrayList<RobotCallProcessStatCache>();
		//先查询未处理的数据
		List<RobotCallProcessStatCache> list = this.queryRobotCallProcessStatByCondition3(condition);
		if(condition != null) {
			if(isConstantsTody(condition.getStatBeginDate(),condition.getStatEndDate())) {
				//如果查询时间段包括了当天，那么从redis缓存中获取当天实时数据
				List<RobotCallProcessStatCache> cacheList = this.queryTodayRobotProcessStat(condition);
				if(cacheList != null && !cacheList.isEmpty()) {
					list.addAll(cacheList);
				}
			}
		}
		if(list != null && !list.isEmpty()) {
			//开始将查询到的数据进行合并处理，同以domain+aiAnswer为维度进行统计
			Map<String, List<RobotCallProcessStatCache>> aiAnswerAGroupMap = new LinkedHashMap<String, List<RobotCallProcessStatCache>>();
			GroupUtils.listGroup2Map(list, aiAnswerAGroupMap, RobotCallProcessStatCache.class, "getDomainandaianswer");// 根据domain+aiAnswer分组
			for (Map.Entry<String, List<RobotCallProcessStatCache>> aianswerGroupEntry : aiAnswerAGroupMap.entrySet()) {
				List<RobotCallProcessStatCache> aiAnswerList = aianswerGroupEntry.getValue();	//	同一组domain+aiAnswer数据（开始进行汇总）
				if(aiAnswerList!=null && !aiAnswerList.isEmpty()) {
					RobotCallProcessStatCache huizongRobotCallProcessStat = new RobotCallProcessStatCache();
					huizongRobotCallProcessStat.setTemplateId(aiAnswerList.get(0).getTemplateId());
					huizongRobotCallProcessStat.setCurrentDomain(aiAnswerList.get(0).getCurrentDomain());
					huizongRobotCallProcessStat.setAiAnswer(aiAnswerList.get(0).getAiAnswer());
					huizongRobotCallProcessStat.setDomainType(aiAnswerList.get(0).getDomainType());
					//开始将统计数据汇总（总数、挂断数、拒绝数）
					for(RobotCallProcessStatCache rcps : aiAnswerList) {
						huizongRobotCallProcessStat.setTotalStat(huizongRobotCallProcessStat.getTotalStat()+rcps.getTotalStat()); //总数合并
						//拒绝统计合并
						huizongRobotCallProcessStat.setRefusedStatMap(this.addNum(huizongRobotCallProcessStat.getRefusedStatMap(), rcps.getRefusedStatMap()));
						//挂断统计合并
						huizongRobotCallProcessStat.setHangupStatMap(this.addNum(huizongRobotCallProcessStat.getHangupStatMap(), rcps.getHangupStatMap()));
						//匹配统计合并
						huizongRobotCallProcessStat.setMatchStatMap(this.addNum(huizongRobotCallProcessStat.getMatchStatMap(), rcps.getMatchStatMap()));
					}
					totalList.add(huizongRobotCallProcessStat);
				}
			}
		}
		return totalList;
	}
	
	
	/**
	 * 查询本地落地的数据，并转为cache数据
	 * @param condition
	 * @return
	 */
	private List<RobotCallProcessStatCache> queryRobotCallProcessStatByCondition3(RobotCallProcessStatQueryCondition condition) {
		//查询本地数据库落地的统计数据
		List<RobotCallProcessStat> list = this.queryRobotCallProcessStatByCondition2(condition);
		if(list != null && !list.isEmpty()) {
			List<RobotCallProcessStatCache> cacheList = new ArrayList<RobotCallProcessStatCache>();
			for(RobotCallProcessStat robotCallProcessStat : list) {
				//将表数据转cache对象
				RobotCallProcessStatCache cacheData = this.changeTbObj2Cache(robotCallProcessStat);
				cacheList.add(cacheData);
			}
			return cacheList;
		}
		return new ArrayList<RobotCallProcessStatCache>();
	}
	
	
	/**
	 * 查询本地没有汇总的用户量化分析统计信息
	 * @param condition
	 * @return
	 */
	private List<RobotCallProcessStat> queryRobotCallProcessStatByCondition2(RobotCallProcessStatQueryCondition condition) {
		RobotCallProcessStatExample example = new RobotCallProcessStatExample();
		if(condition!=null) {
			Criteria criteria = example.createCriteria();
			if(StrUtils.isNotEmpty(condition.getUserId())) {
				criteria.andUserIdEqualTo(condition.getUserId());
			}
			if(StrUtils.isNotEmpty(condition.getOrgCode())) {
				//按机构号查询，带上下级关系
				criteria.andOrgCodeLike(condition.getOrgCode()+"%");
			}
			if(StrUtils.isNotEmpty(condition.getTemplateId())) {
				criteria.andTemplateIdEqualTo(condition.getTemplateId());
			}
			if(StrUtils.isNotEmpty(condition.getStatBeginDate()) && StrUtils.isNotEmpty(condition.getStatEndDate())) {
				//统计日期必须为时间段
				if(condition.getStatBeginDate().equals(condition.getStatEndDate())) {
					//如果开始结束日期相等
					criteria.andStatDateEqualTo(condition.getStatBeginDate());
				}else {
					criteria.andStatDateGreaterThanOrEqualTo(condition.getStatBeginDate());
					criteria.andStatDateLessThanOrEqualTo(condition.getStatEndDate());
				}
			}
		}
		List<RobotCallProcessStat> list = robotCallProcessStatMapper.selectByExample(example);
		if(list == null) {
			list = new ArrayList<RobotCallProcessStat>();
		}
		return list;
	}
	
	
	/**
	 * 查询redis缓存用户量化分析数据
	 * @param condition
	 * @return
	 */
	public List<RobotCallProcessStatCache> queryTodayRobotProcessStat(RobotCallProcessStatQueryCondition condition) {
		if(condition != null) {
			String userId = condition.getUserId();	//用户id
			String orgCode = condition.getOrgCode();	//机构号
			String templateId = condition.getTemplateId();	//模板ID
			if(StrUtils.isNotEmpty(userId)) {
				//根据用户ID、模板编号查询
				Map<String,RobotCallProcessStatCache> robotCacheMap = robotCacheService.queryUserCallStatByUserAndTemplate(userId, templateId);
				return genTemplateCallCache(robotCacheMap);
			}else if(StrUtils.isNotEmpty(orgCode)) {
				//根据机构查询
				Map<String,List<Map<String,RobotCallProcessStatCache>>> allMap = robotCacheService.queryAllRobotCallProcessStat(templateId);
				if(allMap != null && !allMap.isEmpty()) {
					return this.genAllCallCacheByOrg(allMap, orgCode);
				}
			}else if(StrUtils.isEmpty(userId) && StrUtils.isEmpty(orgCode)) {
				//查询所有，然后根据模板ID匹配
				Map<String,List<Map<String,RobotCallProcessStatCache>>> allMap = robotCacheService.queryAllRobotCallProcessStat(templateId);
				if(allMap != null && !allMap.isEmpty()) {
					return this.genAllCallCache(allMap);
				}
			}
		}
		return null;
	}
	
	
	
	/**
	 * 校验开始日期-结束日期，是否包含了当天
	 * @param beginStatDate
	 * @param endStatDate
	 * @return
	 */
	private boolean isConstantsTody(String beginStatDate,String endStatDate) {
		String today = DateUtil.getCurrentymd();	//当天
		if(StrUtils.isNotEmpty(beginStatDate) && StrUtils.isNotEmpty(endStatDate)) {
			if(today.compareTo(beginStatDate)>=0 && today.compareTo(endStatDate)<=0) {
				return true;
			}
		}else if(StrUtils.isEmpty(beginStatDate) && StrUtils.isEmpty(endStatDate)) {
			//如果请求时间段都为空，那么也要查缓存中的数据
			return true;
		}
		return false;
	}
	
	/**
	 * 将缓存对象转为表对象
	 * @return
	 */
	private RobotCallProcessStat changeCache2TbObj(RobotCallProcessStatCache robotCallProcessStatCache) {
		if(robotCallProcessStatCache != null) {
			RobotCallProcessStat robotCallProcessStat = new RobotCallProcessStat();
			//属性拷贝
			BeanUtil.copyProperties(robotCallProcessStatCache, robotCallProcessStat);
			robotCallProcessStat.setRefusedStat(robotCallProcessStatCache.getRefusedStatMap()==null?null:JsonUtils.bean2Json(robotCallProcessStatCache.getRefusedStatMap()));
			robotCallProcessStat.setHangupStat(robotCallProcessStatCache.getHangupStatMap()==null?null:JsonUtils.bean2Json(robotCallProcessStatCache.getHangupStatMap()));
			robotCallProcessStat.setMatchStat(robotCallProcessStatCache.getMatchStatMap()==null?null:JsonUtils.bean2Json(robotCallProcessStatCache.getMatchStatMap()));
			return robotCallProcessStat;
		}
		return null;
	}
	
	
	/**
	 * 将表对象对象转为缓存
	 * @return
	 */
	private RobotCallProcessStatCache changeTbObj2Cache(RobotCallProcessStat robotCallProcessStat) {
		if(robotCallProcessStat != null) {
			RobotCallProcessStatCache cache = new RobotCallProcessStatCache();
			//属性拷贝
			BeanUtil.copyProperties(robotCallProcessStat, cache);
			cache.setRefusedStatMap(robotCallProcessStat.getRefusedStat()==null?null:JsonUtils.json2Bean(robotCallProcessStat.getRefusedStat(), Map.class));
			cache.setHangupStatMap(robotCallProcessStat.getHangupStat()==null?null:JsonUtils.json2Bean(robotCallProcessStat.getHangupStat(), Map.class));
			cache.setMatchStatMap(robotCallProcessStat.getMatchStat()==null?null:JsonUtils.json2Bean(robotCallProcessStat.getMatchStat(), Map.class));
			return cache;
		}
		return null;
	}
	
	
	/**
	 * 将缓存中用户模板的统计数据转为对象返回
	 * @param robotCacheMap
	 * @return
	 */
	private List<RobotCallProcessStatCache> genTemplateCallCache(Map<String,RobotCallProcessStatCache> robotCacheMap) {
		List<RobotCallProcessStatCache> list = new ArrayList<RobotCallProcessStatCache>();
		if(robotCacheMap != null && !robotCacheMap.isEmpty()) {
			for (Map.Entry<String,RobotCallProcessStatCache> robotCacheEntry : robotCacheMap.entrySet()) {
				list.add(robotCacheEntry.getValue());
			}
		}
		return list;
	}
	
	/**
	 * 将缓存中用户的统计数据转为对象返回
	 * @param userRobotList
	 * @return
	 */
	private List<RobotCallProcessStatCache> genUserCallCache(List<Map<String,RobotCallProcessStatCache>> userRobotList){
		List<RobotCallProcessStatCache> list = new ArrayList<RobotCallProcessStatCache>();
		if(userRobotList!=null && !userRobotList.isEmpty()) {
			for(Map<String,RobotCallProcessStatCache> robotCacheMap : userRobotList) {
				list.addAll(genTemplateCallCache(robotCacheMap));
			}
		}
		return list;
	}
	
	/**
	 * 将缓存中按机构统计数据转对象返回
	 * @param allMap
	 * @param templateId
	 * @return
	 */
	private List<RobotCallProcessStatCache> genAllCallCacheByOrg(Map<String,List<Map<String,RobotCallProcessStatCache>>> allMap,String orgCode){
		List<RobotCallProcessStatCache> list = new ArrayList<RobotCallProcessStatCache>();
		if(allMap != null && !allMap.isEmpty()){
			for (Map.Entry<String,List<Map<String,RobotCallProcessStatCache>>> allCacheEntry : allMap.entrySet()) {
				String userId = allCacheEntry.getKey();	//用户ID
				List<Map<String,RobotCallProcessStatCache>> userList = allCacheEntry.getValue();
				if(userList != null && !userList.isEmpty()) {
					for(Map<String,RobotCallProcessStatCache> templateCacheMap : userList) {
						for (Map.Entry<String,RobotCallProcessStatCache> newMapEntry : templateCacheMap.entrySet()) {
							RobotCallProcessStatCache cache = newMapEntry.getValue();
							if(StrUtils.isNotEmpty(cache.getOrgCode())) {
								//如果参数是管理员，机构如：1.1 ; 缓存数据是操作员:1.1.1，那么也应该可以统计的到
								if(cache.getOrgCode().startsWith(orgCode)) {
									list.add(newMapEntry.getValue());
								}
							}
						}
					}
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 将缓存中所有用户统计数据转对象返回
	 * @param allMap
	 * @param templateId
	 * @return
	 */
	private List<RobotCallProcessStatCache> genAllCallCache(Map<String,List<Map<String,RobotCallProcessStatCache>>> allMap){
		List<RobotCallProcessStatCache> list = new ArrayList<RobotCallProcessStatCache>();
		if(allMap != null && !allMap.isEmpty()){
			for (Map.Entry<String,List<Map<String,RobotCallProcessStatCache>>> allCacheEntry : allMap.entrySet()) {
				String userId = allCacheEntry.getKey();	//用户ID
				List<Map<String,RobotCallProcessStatCache>> userList = allCacheEntry.getValue();
				if(userList != null && !userList.isEmpty()) {
					for(Map<String,RobotCallProcessStatCache> templateCacheMap : userList) {
						for (Map.Entry<String,RobotCallProcessStatCache> newMapEntry : templateCacheMap.entrySet()) {
							list.add(newMapEntry.getValue());
						}
					}
				}
			}
		}
		return list;
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
