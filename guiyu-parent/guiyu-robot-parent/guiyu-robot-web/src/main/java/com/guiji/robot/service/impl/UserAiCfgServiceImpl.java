package com.guiji.robot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.UserAiCfgBaseInfoMapper;
import com.guiji.robot.dao.UserAiCfgHisInfoMapper;
import com.guiji.robot.dao.UserAiCfgInfoMapper;
import com.guiji.robot.dao.entity.UserAiCfgBaseInfo;
import com.guiji.robot.dao.entity.UserAiCfgBaseInfoExample;
import com.guiji.robot.dao.entity.UserAiCfgHisInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfoExample;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.service.IUserAiCfgService;
import com.guiji.robot.service.vo.AiInuseCache;
import com.guiji.robot.service.vo.UserResourceCache;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: UserAiCfgServiceImpl 
* @Description: 用户-机器人配置服务
* @date 2018年11月16日 下午2:21:53 
* @version V1.0  
*/
@Service
public class UserAiCfgServiceImpl implements IUserAiCfgService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String LOCK_NAME = "LOCK_ROBOT_USER_AI_CFG_";	//资源锁名称
	@Autowired
	private UserAiCfgBaseInfoMapper userAiCfgBaseInfoMapper;
	@Autowired
	private UserAiCfgInfoMapper userAiCfgInfoMapper;
	@Autowired
	private UserAiCfgHisInfoMapper userAiCfgHisInfoMapper;
	@Autowired
	AiCacheService aiCacheService;
	@Autowired
	DistributedLockHandler distributedLockHandler;
	
	
	/**
	 * 保存或者更新一条用户基本信息（不开放出去，必须调用变更服务）
	 * @param userAiCfgBaseInfo
	 * @return
	 */
	private UserAiCfgBaseInfo saveOrUpdate(UserAiCfgBaseInfo userAiCfgBaseInfo) {
		if(userAiCfgBaseInfo != null) {
			if(StrUtils.isEmpty(userAiCfgBaseInfo.getId())) {
				//如果主键为空，那么新增一条信息
				userAiCfgBaseInfo.setCrtTime(new Date());
				userAiCfgBaseInfoMapper.insert(userAiCfgBaseInfo);
			}else {
				//主键不为空，更新信息
				userAiCfgBaseInfoMapper.updateByPrimaryKey(userAiCfgBaseInfo);
			}
		}
		return userAiCfgBaseInfo;
	}
	
	
	/**
	 * 机器人数量总控配置
	 * @param userAiCfgBaseInfo
	 * @return
	 */
	public UserAiCfgBaseInfo putupUserCfgBase(UserAiCfgBaseInfo userAiCfgBaseInfo) {
		if(userAiCfgBaseInfo != null) {
			String id = userAiCfgBaseInfo.getId();
			if(StrUtils.isNotEmpty(id)) {
				//更新
				throw new RobotException(AiErrorEnum.AI00060024.getErrorCode(),AiErrorEnum.AI00060024.getErrorMsg());
			}else {
				//新增
				//1、初始化一条用户机器人线路拆分
				UserAiCfgInfo userAiCfgInfo = new UserAiCfgInfo();
				BeanUtil.copyProperties(userAiCfgBaseInfo, userAiCfgInfo);
				userAiCfgInfo.setAiNum(userAiCfgBaseInfo.getAiTotalNum()); //机器人总数(初始化时为全部)
				this.userAiCfgChange(userAiCfgInfo);
			}
			//2、新增或者更新基本信息
			this.saveOrUpdate(userAiCfgBaseInfo);
		}
		return userAiCfgBaseInfo;
	}
	
	
	/**
	 * 查询用户机器人配置基本信息
	 * @param userId
	 * @return
	 */
	@Override
	public UserAiCfgBaseInfo queryUserAiCfgBaseInfoByUserId(String userId) {
		if(StrUtils.isNotEmpty(userId)) {
			UserAiCfgBaseInfoExample example = new UserAiCfgBaseInfoExample();
			example.createCriteria().andUserIdEqualTo(userId);
			List<UserAiCfgBaseInfo> list = userAiCfgBaseInfoMapper.selectByExample(example);
			if(ListUtil.isNotEmpty(list)) {
				return list.get(0);
			}
		}
		return null;
	}
	
	
	/**
	 * 保存或者更新一条用户-机器人配置信息（不开放出去，必须调用变更服务）
	 * 同时记录历史
	 * @param userAiCfgInfo
	 * @return
	 */
	private UserAiCfgInfo saveOrUpdate(UserAiCfgInfo userAiCfgInfo) {
		if(userAiCfgInfo != null) {
			UserAiCfgHisInfo record = new UserAiCfgHisInfo();
			if(StrUtils.isEmpty(userAiCfgInfo.getId())) {
				//如果主键为空，那么新增一条信息
				userAiCfgInfo.setStatus(RobotConstants.USER_CFG_STATUS_S); //正常状态
				userAiCfgInfo.setCrtTime(new Date());
				userAiCfgInfoMapper.insert(userAiCfgInfo);
				record.setHandleType(RobotConstants.HANDLE_TYPE_A); //新增
			}else {
				//主键不为空，更新信息
				userAiCfgInfoMapper.updateByPrimaryKey(userAiCfgInfo);
				record.setHandleType(RobotConstants.HANDLE_TYPE_U); //更新
			}
			//记录一条用户账户变更历史
			record.setCrtTime(new Date());
			record.setBusiId(userAiCfgInfo.getId());
			BeanUtil.copyProperties(userAiCfgInfo, record);
			userAiCfgHisInfoMapper.insert(record);
		}
		return userAiCfgInfo;
	}
	
	
	/**
	 * 根据用户编号查询用户-机器人配置信息列表
	 * @return
	 */
	@Override
	public List<UserAiCfgInfo> queryUserAiCfgListByUserId(String userId){
		if(userId != null) {
			UserAiCfgInfoExample example = new UserAiCfgInfoExample();
			example.createCriteria().andUserIdEqualTo(userId);
			return userAiCfgInfoMapper.selectByExample(example);
		}
		return null;
	}
	
	
	/**
	 * 根据用户编号查询用户符合话术模板的配置列表
	 * @return
	 */
	@Override
	public List<UserAiCfgInfo> queryUserAiCfgListByUserId(String userId,String templateId){
		if(StrUtils.isNotEmpty(userId) && StrUtils.isNotEmpty(templateId)) {
			//查询用户所有模板
			List<UserAiCfgInfo> list = this.queryUserAiCfgListByUserId(userId);
			List<UserAiCfgInfo> rtnList = new ArrayList<UserAiCfgInfo>();
			if(ListUtil.isNotEmpty(list)) {
				for(UserAiCfgInfo cfg : list) {
					if(StrUtils.isNotEmpty(cfg.getTemplateIds()) && cfg.getTemplateIds().contains(templateId)) {
						//如果该配置可以使用该话术模板，返回
						rtnList.add(cfg);
					}
				}
			}
			return rtnList;
		}
		return null;
	}
	
	
	/**
	 * 查询用户下可以使用某个话术的机器人列表
	 * @param userId
	 * @param templateId
	 * @return
	 */
	@Override
	public List<UserAiCfgInfo> queryUserAiCfgListByUserIdAndTemplate(String userId,String templateId){
		if(StrUtils.isNotEmpty(userId) && StrUtils.isNotEmpty(templateId)) {
			//查询用户全部配置信息
			List<UserAiCfgInfo> userAllAiCfgList = this.queryUserAiCfgListByUserId(userId);
			if(ListUtil.isNotEmpty(userAllAiCfgList)) {
				Iterator<UserAiCfgInfo> it = userAllAiCfgList.iterator();
				while(it.hasNext()) {
					UserAiCfgInfo aiCfg = it.next();
					if(!aiCfg.getTemplateIds().contains(templateId)) {
						it.remove();
					}
				}
			}
			return  userAllAiCfgList;
		}
		return null;
	}
	
	
	/**
	 * 用户资源变更服务
	 * 用户新增线路、减少线路、变更绑定的模板 都需要更新下用户资源缓存数据
	 * 因用户资源变更影响较大，所以此处增加缓存锁，防止并发的情况
	 * @param userAiCfgInfo
	 * @return
	 */
	@Override
	@Transactional
	public UserAiCfgInfo userAiCfgChange(UserAiCfgInfo userAiCfgInfo) {
		Lock lock = new Lock(LOCK_NAME+userAiCfgInfo.getUserId(), LOCK_NAME+userAiCfgInfo.getUserId());
		if (distributedLockHandler.tryLock(lock, 30*1000, 50, 3*60*1000)) { // 尝试30s,每30ms尝试一次，持锁时间为3分钟
			try {
				String id = userAiCfgInfo.getId();
				//查询用户资源配置缓存数据
				UserResourceCache userResourceCache = aiCacheService.getUserResource(userAiCfgInfo.getUserId());
				if(userResourceCache == null) {
					//初始化
					userResourceCache = new UserResourceCache();
					userResourceCache.setUserId(userAiCfgInfo.getUserId());
				}
				if(StrUtils.isNotEmpty(id)) {
					//Id不为空，更新
					//更新1个配置项
					//查询用户机器人数量
					UserAiCfgInfo existUserAiCfgInfo = userAiCfgInfoMapper.selectByPrimaryKey(id);
					userAiCfgInfo.setStatus(existUserAiCfgInfo.getStatus());
					userAiCfgInfo.setCrtTime(existUserAiCfgInfo.getCrtTime());
					//如果本次调整的机器人数量减少了，或者话术模板变更了，都认为机器人变更是资源减少的，此处设置下减少，机器人管理那边会清空机器人，重新根据配置拉起。
					if(userAiCfgInfo.getAiNum()<existUserAiCfgInfo.getAiNum()) {
						userResourceCache.setAiNum(userResourceCache.getAiNum()+(userAiCfgInfo.getAiNum()-existUserAiCfgInfo.getAiNum())); //变更后数量
						userResourceCache.setChgStatus(RobotConstants.USER_CHG_STATUS_S); //资源变更-减少
					}else if(userAiCfgInfo.getAiNum()>existUserAiCfgInfo.getAiNum()) {
						//本次变更机器人数量增加量
						int addAiNum = userResourceCache.getAiNum()+(userAiCfgInfo.getAiNum()-existUserAiCfgInfo.getAiNum());
						//新增配置项
						if(this.isOverAiNumCheck(userAiCfgInfo.getUserId(), addAiNum)) {
							//校验是否有超过用户总机器人数量
							throw new RobotException(AiErrorEnum.AI00060021.getErrorCode(),AiErrorEnum.AI00060021.getErrorMsg());
						}
						userResourceCache.setAiNum(addAiNum); //变更后数量
						if(!RobotConstants.USER_CHG_STATUS_S.equals(userResourceCache.getChgStatus())) {
							//如果目前的资源变更状态是减少，处理完成前，那么不能变为增加
							userResourceCache.setChgStatus(RobotConstants.USER_CHG_STATUS_A); //资源变更-增加
						}
					}
					if(!userAiCfgInfo.getTemplateIds().equals(existUserAiCfgInfo.getTemplateIds())) {
						//如果模板发生了变化，不管机器人数量增加与否，都认为资源发生了减少，机器人重新分配
						userResourceCache.setChgStatus(RobotConstants.USER_CHG_STATUS_S); //资源变更-减少
					}
				}else {
					//新增配置项
					if(this.isOverAiNumCheck(userAiCfgInfo.getUserId(), userAiCfgInfo.getAiNum())) {
						//校验是否有超过用户总机器人数量
						throw new RobotException(AiErrorEnum.AI00060021.getErrorCode(),AiErrorEnum.AI00060021.getErrorMsg());
					}
					//新增下缓存中用户机器人总数量
					userResourceCache.setAiNum(userResourceCache.getAiNum()+userAiCfgInfo.getAiNum());
					if(!RobotConstants.USER_CHG_STATUS_S.equals(userResourceCache.getChgStatus())) {
						//如果目前的资源变更状态是减少，处理完成前，那么不能变为增加
						userResourceCache.setChgStatus(RobotConstants.USER_CHG_STATUS_A); //资源变更-增加
					}
				}
				//更新用户资源缓存，重新检查下，如果现在用户本身没有分配的资源，不需要设置状态
				List<AiInuseCache> userAiInUseLis = aiCacheService.queryUserAiInUseList(userAiCfgInfo.getUserId());
				if(userAiInUseLis == null || userAiInUseLis.isEmpty()) {
					userResourceCache.setChgStatus(null);
				}
				aiCacheService.putUserResource(userResourceCache);
				//落库
				userAiCfgInfo = this.saveOrUpdate(userAiCfgInfo);
				return userAiCfgInfo;
			} catch (RobotException e) {
				throw e; 
			} catch (Exception e1) {
				logger.error("机器人线路拆分保存异常！",e1);
				throw new RobotException(AiErrorEnum.AI00060022.getErrorCode(),AiErrorEnum.AI00060022.getErrorMsg());
			}finally {
				//释放锁
				distributedLockHandler.releaseLock(lock);
			}
		}else {
			logger.warn("用户机器人配置变更未能获取锁！！！");
		}
		return null;
	}
	
	
	/**
	 * 删除用户一条资源配置信息
	 * @param userId
	 * @param id
	 */
	@Override
	@Transactional
	public void delUserCfg(String userId,String id) {
		if(StrUtils.isNotEmpty(id) && StrUtils.isNotEmpty(userId)) {
			Lock lock = new Lock(LOCK_NAME+userId, LOCK_NAME+userId);
			if (distributedLockHandler.tryLock(lock, 30*1000, 50, 3*60*1000)) { // 尝试30s,每30ms尝试一次，持锁时间为3分钟
				try {
					//根据id查询用户存量缓存数据
					UserAiCfgInfo existUserAiCfgInfo = userAiCfgInfoMapper.selectByPrimaryKey(id);
					if(existUserAiCfgInfo != null) {
						if(RobotConstants.USER_CFG_STATUS_S.equals(existUserAiCfgInfo.getStatus())) {
							//如果删除的是正常的数据，那么需要做下变更
							//用户资源缓存
							UserResourceCache userResourceCache = aiCacheService.getUserResource(existUserAiCfgInfo.getUserId());
							if(userResourceCache == null) {
								//初始化
								userResourceCache = new UserResourceCache();
								userResourceCache.setUserId(existUserAiCfgInfo.getUserId());
							}
							userResourceCache.setAiNum(userResourceCache.getAiNum()- existUserAiCfgInfo.getAiNum()); //机器人数量变更
							userResourceCache.setChgStatus(RobotConstants.USER_CHG_STATUS_S); //资源变更-减少
							//更新用户资源缓存，重新检查下，如果现在用户本身没有分配的资源，不需要设置状态
							List<AiInuseCache> userAiInUseLis = aiCacheService.queryUserAiInUseList(existUserAiCfgInfo.getUserId());
							if(userAiInUseLis == null || userAiInUseLis.isEmpty()) {
								userResourceCache.setChgStatus(null);
							}
							aiCacheService.putUserResource(userResourceCache);
						}else {
							logger.info("本次删除的是状态不正常的数据{}，不需要触发资源变更！",id);
						}
						//删除数据
						userAiCfgInfoMapper.deleteByPrimaryKey(id);
						//记录一条用户账户变更历史
						UserAiCfgHisInfo record = new UserAiCfgHisInfo();
						record.setCrtTime(new Date());
						record.setBusiId(existUserAiCfgInfo.getId());
						BeanUtil.copyProperties(existUserAiCfgInfo, record);
						record.setHandleType(RobotConstants.HANDLE_TYPE_D); //删除
						userAiCfgHisInfoMapper.insert(record);
					}else {
						logger.error("要删除的数据{}已不存在..",id);
					}
				} catch (RobotException e) {
					throw e; 
				} catch (Exception e1) {
					logger.error("机器人线路拆分删除异常！",e1);
					throw new RobotException(AiErrorEnum.AI00060023.getErrorCode(),AiErrorEnum.AI00060023.getErrorMsg());
				}finally {
					//释放锁
					distributedLockHandler.releaseLock(lock);
				}
			}else {
				logger.error("用户资源删除未能获取到锁!");
			}
		}else {
			logger.error("user id/ id 不能为空！");
		}
	}
	
	
	/**
	 * 校验用户本次新增机器人总数是否有超过总控数量
	 * @param userId
	 * @param addAiNum
	 * @return
	 */
	private boolean isOverAiNumCheck(String userId,int addAiNum) {
		if(StrUtils.isNotEmpty(userId) && addAiNum >0) {
			//查询用户总控机器人数量
			UserAiCfgBaseInfo userBaseInfo = this.queryUserAiCfgBaseInfoByUserId(userId);
			int totalNum = userBaseInfo.getAiTotalNum();
			//查询用户现有机器人数量配置
			List<UserAiCfgInfo> existCfgList = this.queryUserAiCfgListByUserId(userId);
			int existAiNum = 0;
			if(ListUtil.isNotEmpty(existCfgList)) {
				for(UserAiCfgInfo cfgInfo : existCfgList) {
					if(RobotConstants.USER_CFG_STATUS_S.equals(cfgInfo.getStatus())) {
						//正常状态
						existAiNum = existAiNum + cfgInfo.getAiNum();
					}
				}
			}
			if(existAiNum+addAiNum > totalNum) {
				return true;
			}
		}
		return false;
	}
}
