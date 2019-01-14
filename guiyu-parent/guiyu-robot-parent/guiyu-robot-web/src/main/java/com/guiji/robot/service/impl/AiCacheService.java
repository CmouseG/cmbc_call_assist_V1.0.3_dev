package com.guiji.robot.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.robot.service.vo.AiFlowSentenceCache;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.HsReplace;
import com.guiji.robot.service.vo.UserResourceCache;
import com.guiji.robot.util.ListUtil;
import com.guiji.robot.util.ReadTxtUtil;
import com.guiji.robot.util.SystemUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: AiCacheService 
* @Description: AI数据缓存服务
* @date 2018年11月19日 下午5:27:28 
* @version V1.0  
*/
@Service
public class AiCacheService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	IUserAiCfgService iUserAiCfgService;
	@Value("${file.hushuDir}")
    private String hushuDir;	//话术模板存放目录

	/**
	 * 根据用户id查询用户的资源缓存信息
	 * 如果用户缓存不存在，那么重新查询后设置环境
	 * @param userId
	 * @return
	 */
	public UserResourceCache getUserResource(String userId) {
		Object cacheObj = redisUtil.hget(RobotConstants.ROBOT_USER_RESOURCE, userId);
		if(cacheObj == null) {
			//重新查询
			List<UserAiCfgInfo> list = iUserAiCfgService.queryUserAiCfgListByUserId(userId);
			UserResourceCache userResourceCache = new UserResourceCache();
			int aiNum = 0;
			if(ListUtil.isNotEmpty(list)) {
				for(UserAiCfgInfo cfg : list) {
					if(RobotConstants.USER_CFG_STATUS_S == cfg.getStatus()) {
						//如果账户是正常状态
						aiNum = aiNum + cfg.getAiNum();
					}
				}
			}
			userResourceCache.setUserId(userId);
			userResourceCache.setAiNum(aiNum);
			userResourceCache.setChgStatus(RobotConstants.USER_CHG_STATUS_A); //初始化增加
			//设置按模板拆分的机器人数量
			userResourceCache.setTempAiNumMap(iUserAiCfgService.queryTemplateAi(userId));
			//放入redis
			this.putUserResource(userResourceCache);
			return userResourceCache;
		}else {
			return (UserResourceCache)cacheObj;
		}
	}
	
	
	
	/**
	 * 新增/更新一个用户的缓存资源
	 * @param userResourceCache
	 */
	public void putUserResource(UserResourceCache userResourceCache){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(userResourceCache.getUserId(), userResourceCache);
		//提交到redis
		redisUtil.hmset(RobotConstants.ROBOT_USER_RESOURCE, map);
	}
	
	
	/**
	 * 删除用户资源缓存
	 * @param userId
	 */
	public void delUserResource(String userId) {
		redisUtil.hdel(RobotConstants.ROBOT_USER_RESOURCE,userId);
	}
	
	
	/**
	 * 初始化AI资源池
	 * @param list
	 */
	public void initAiPool(List<AiInuseCache> list){
		if(ListUtil.isNotEmpty(list)) {
			Map<String,Object> map = new HashMap<String,Object>();
			for(AiInuseCache aiInuse : list) {
				map.put(aiInuse.getAiNo(), aiInuse);
			}
			redisUtil.hmset(RobotConstants.ROBOT_POOL_AI, map);
		}
	}
	
	/**
	 * 更新AI资源池AI数据
	 * @param aiInuse
	 * @return
	 */
	public void changeAiPool(AiInuseCache aiInuse) {
		redisUtil.hset(RobotConstants.ROBOT_POOL_AI, aiInuse.getAiNo(), aiInuse);
	}
	
	/**
	 * 清空AI资源池缓存
	 */
	public void delAiPools() {
		redisUtil.del(RobotConstants.ROBOT_POOL_AI);
	}
	
	/**
	 * 查询ai资源池中所有机器人
	 * @return
	 */
	public List<AiInuseCache> queryAiPools(){
		Map<Object,Object> allMap = redisUtil.hmget(RobotConstants.ROBOT_POOL_AI);
		if(allMap != null && !allMap.isEmpty()) {
			List<AiInuseCache> list = new ArrayList<AiInuseCache>();
			for (Map.Entry<Object,Object> aiEntry : allMap.entrySet()) { 
				AiInuseCache aiInuse = (AiInuseCache) aiEntry.getValue();
				list.add(aiInuse);
			}
			return list;
		}
		return null;
	}
	
	
	
	/**
	 * 将分配好的机器人放入缓存
	 * userid{aiNo-aiInuse}
	 * @param userId
	 * @param list
	 * @return
	 */
	public void cacheUserAiAssign(String userId,List<AiInuseCache> list){
		if(StrUtils.isNotEmpty(userId) && ListUtil.isNotEmpty(list)) {
			Map<String,Object> map = new HashMap<String,Object>();
			for(AiInuseCache aiInuse : list) {
				map.put(aiInuse.getAiNo(), aiInuse);
			}
			redisUtil.hmset(RobotConstants.ROBOT_ASSIGN_AI+userId, map);
		}
	}
	
	/**
	 * 查询用户现在已分配的机器人
	 * @param userId
	 * @return
	 */
	public List<AiInuseCache> queryUserAiInUseList(String userId){
		Map<Object,Object> allMap = redisUtil.hmget(RobotConstants.ROBOT_ASSIGN_AI+userId);
		if(allMap != null && !allMap.isEmpty()) {
			List<AiInuseCache> list = new ArrayList<AiInuseCache>();
			for (Map.Entry<Object,Object> aiEntry : allMap.entrySet()) { 
				AiInuseCache aiInuse = (AiInuseCache) aiEntry.getValue();
				list.add(aiInuse);
			}
			return list;
		}
		return null;
	}
	
	
	/**
	 * 删除某个用户分配机器人缓存数据
	 * @param userId
	 */
	public void delUserAis(String userId) {
		redisUtil.del(RobotConstants.ROBOT_ASSIGN_AI+userId);
	}
	
	
	/**
	 * 删除某个用户某个机器人的缓存数据
	 * @param userId
	 */
	public void delUserAi(String userId,String aiNo) {
		redisUtil.hdel(RobotConstants.ROBOT_ASSIGN_AI+userId,aiNo);
	}
	
	
	/**
	 * 查询缓存中所有已分配的机器人
	 * @return
	 */
	public Map<String,List<AiInuseCache>> queryAllAiInUse(){
		Map<String,List<AiInuseCache>> allAiMap = new HashMap<String,List<AiInuseCache>>();
		//查询所有在缓存中的用户数据
		Set<String> userSet = redisUtil.getAllKeyMatch(RobotConstants.ROBOT_ASSIGN_AI);
		if(userSet != null && !userSet.isEmpty()) {
			for(String key:userSet) {
				String userId = key.substring(key.indexOf(RobotConstants.ROBOT_ASSIGN_AI)+14);	//将key中的userid截取出来
				List<AiInuseCache> list = this.queryUserAiInUseList(userId);
				allAiMap.put(userId, list);
			}
		}
		return allAiMap;
	}
	
	
	/**
	 * 根据用户ID和机器人编号查询某个在使用的机器人信息
	 * @param userId
	 * @param aiNo
	 * @return
	 */
	public AiInuseCache queryAiInuse(String userId,String aiNo) {
		//查询某个ai的值
		Object cacheObj = redisUtil.hget(RobotConstants.ROBOT_ASSIGN_AI+userId, aiNo);
		if(cacheObj != null) {
			return (AiInuseCache) cacheObj;
		}
		return null;
	}
	
	
	/**
	 * 更新缓存数据状态
	 * @param aiInuse
	 * @return
	 */
	public void changeAiInUse(AiInuseCache aiInuse) {
		redisUtil.hset(RobotConstants.ROBOT_ASSIGN_AI+aiInuse.getUserId(), aiInuse.getAiNo(), aiInuse);
	}
	
	
	/**
	 * 查询话术模板数据
	 */
	public HsReplace queyHsReplace(String templateId){
		if(StrUtils.isNotEmpty(templateId)) {
			Object cacheObj = redisUtil.hget(RobotConstants.ROBOT_TEMPLATE_RESOURCE, templateId);
			if(cacheObj == null) {
				//重新查询
				//获取话术模板json文件
				String replaceFilePath = this.getHsJsonPath(templateId);
				if(!new File(replaceFilePath).exists()) {
					logger.info("话术模板{},replace.json文件{}不存在，不需要tts合成",templateId,replaceFilePath);
					//如果replace.json不存在，那么本话术模板不需要合成TTS
					HsReplace hsReplace = new HsReplace();
					hsReplace.setTemplate_tts_flag(false);
					//提交到redis
					Map<String,Object> map = new HashMap<String,Object>();
					map.put(templateId, hsReplace);
					redisUtil.hmset(RobotConstants.ROBOT_TEMPLATE_RESOURCE, map);
					return hsReplace;
				}
				//读取本地话术模板文件
				String json = ReadTxtUtil.readTxtFile(replaceFilePath);
				//读取json文件获取数据
				HsReplace hsReplace = JsonUtils.json2Bean(json, HsReplace.class);
				if(hsReplace != null) {
					//提交到redis
					Map<String,Object> map = new HashMap<String,Object>();
					map.put(templateId, hsReplace);
					redisUtil.hmset(RobotConstants.ROBOT_TEMPLATE_RESOURCE, map);
					return hsReplace;
				}else {
					logger.error("读取本地话术模板replace.json文件失败，文件路径：{}",replaceFilePath);
					throw new RobotException(AiErrorEnum.AI00060016.getErrorCode(),AiErrorEnum.AI00060016.getErrorMsg());
				}
			}else {
				//如果查到了，直接返回
				return (HsReplace) cacheObj;
			}
		}else {
			return null;
		}
	}
	
	
	/**
	 * 从缓存中将模板清除掉后重新读取
	 * @param templateId
	 */
	public void updateHsReplace(String templateId) {
		if(StrUtils.isNotEmpty(templateId)) {
			Object cacheObj = redisUtil.hget(RobotConstants.ROBOT_TEMPLATE_RESOURCE, templateId);
			if(cacheObj != null) {
				//缓存中有，需要删除掉，重新拉取
				logger.info("模板{}存在缓存中，需要更新缓存",templateId);
				//删除缓存
				redisUtil.hdel(RobotConstants.ROBOT_TEMPLATE_RESOURCE,templateId);
				//重新获取redis
				this.queyHsReplace(templateId);
				
			}else {
				logger.info("模板{}没有缓存，不需要处理",templateId);
			}
		}
	}
	
	
	
	/**
	 * 新增消息流数据
	 * @param userResourceCache
	 */
	public void putFlowSentence(AiFlowSentenceCache aiFlowSentenceCache){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(String.valueOf(aiFlowSentenceCache.getTimestamp()), aiFlowSentenceCache);
		//提交到redis（通话流数据，缓存10分钟即可）
		redisUtil.hset(RobotConstants.ROBOT_SENTENCE_RESOURCE+aiFlowSentenceCache.getSeqId(), String.valueOf(aiFlowSentenceCache.getTimestamp()), aiFlowSentenceCache,10*60);
	}
	
	
	/**
	 * 根据会话Id查询消息流数据
	 * @param userId
	 * @return
	 */
	public Map<Long,AiFlowSentenceCache> queryFlowSentence(String seqId){
		Map<Object,Object> allMap = redisUtil.hmget(RobotConstants.ROBOT_SENTENCE_RESOURCE+seqId);
		if(allMap != null && !allMap.isEmpty()) {
			Map<Long,AiFlowSentenceCache> rtnMap = new HashMap<Long,AiFlowSentenceCache>();
			for (Map.Entry<Object,Object> aiEntry : allMap.entrySet()) { 
				rtnMap.put(Long.valueOf((String)aiEntry.getKey()), (AiFlowSentenceCache)aiEntry.getValue());
			}
			return rtnMap;
		}
		return null;
	}
	
	
	/**
	 * 删除某个会话ID下的消息流
	 * @param seqId 会话id
	 */
	public void delAllSentenceBySeqId(String seqId) {
		redisUtil.del(RobotConstants.ROBOT_SENTENCE_RESOURCE+seqId);
	}
	
	
	/**
	 * 删除某个某个会话下某个时间点的消息流
	 * @param userId
	 */
	public void delSentence(String seqId,Long timestamp) {
		redisUtil.hdel(RobotConstants.ROBOT_SENTENCE_RESOURCE+seqId,String.valueOf(timestamp));
	}
	
	
	/**
	 * 获取话术模板replace.json文件路径
	 * @param hushuDirPath
	 * @param ttsVoiceReq
	 * @return
	 */
	private String getHsJsonPath(String templateId) {
		String hushuDirPath = SystemUtil.getRootPath()+hushuDir + "/" + templateId + "/" + templateId + "/"; //话术模板存放目录
		if(!new File(hushuDirPath).exists()) {
			//话术模板检查
			logger.error("话术模板{}在路径{}不存在。。。",templateId,hushuDirPath);
			throw new RobotException(AiErrorEnum.AI00060026.getErrorCode(),AiErrorEnum.AI00060026.getErrorMsg());
		}
		return hushuDirPath + "replace.json";
	}
}
