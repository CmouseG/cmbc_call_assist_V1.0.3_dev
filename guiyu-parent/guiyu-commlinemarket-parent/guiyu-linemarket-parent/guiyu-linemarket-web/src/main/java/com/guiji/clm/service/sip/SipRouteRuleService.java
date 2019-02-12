package com.guiji.clm.service.sip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.clm.constant.ClmConstants;
import com.guiji.clm.dao.SipRouteRuleMapper;
import com.guiji.clm.dao.entity.SipRouteItem;
import com.guiji.clm.dao.entity.SipRouteRule;
import com.guiji.clm.dao.entity.SipRouteRuleExample;
import com.guiji.clm.enm.SipRouteRuleStatusEnum;
import com.guiji.clm.enm.SipRouteRuleTypeEnum;
import com.guiji.clm.exception.ClmErrorEnum;
import com.guiji.clm.exception.ClmException;
import com.guiji.clm.model.SipRouteItemVO;
import com.guiji.clm.model.SipRouteRuleVO;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.utils.DateUtil;
import com.guiji.utils.StrUtils;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;

/** 
* @Description: 代理第三方线路服务
* @Author: weiyunbo
* @date 2019年1月23日 上午10:29:39 
* @version V1.0  
*/
@Slf4j
@Service
public class SipRouteRuleService {
	@Autowired
	SipRouteRuleMapper sipRouteRuleMapper;
	@Autowired
	SipRouteItemService sipRouteItemService;
	@Autowired
	DistributedLockHandler distributedLockHandler;
	
	
	/**
	 * 新增/更新规则路由信息
	 * @param SipRouteRule
	 * @return
	 */
	@Transactional
	public SipRouteRule save(SipRouteRule sipRouteRule) {
		Lock lock = new Lock(ClmConstants.LOCK_LINEMARKET_SAVERULE+sipRouteRule.getUserId(), ClmConstants.LOCK_LINEMARKET_SAVERULE+sipRouteRule.getUserId());
		if (distributedLockHandler.tryLock(lock)) {
			try {
				if(sipRouteRule!=null) {
					if(sipRouteRule.getId()!=null) {
						//更新
						sipRouteRule.setUpdateTime(DateUtil.getCurrent4Time());
						sipRouteRuleMapper.updateByPrimaryKey(sipRouteRule);
					}else {
						//新增
						sipRouteRule.setStatus(SipRouteRuleStatusEnum.OK.getCode());	//默认正常状态
						sipRouteRule.setCrtTime(DateUtil.getCurrent4Time());
						sipRouteRule.setUpdateTime(DateUtil.getCurrent4Time());
						sipRouteRuleMapper.insert(sipRouteRule);
					}
				}
				return sipRouteRule;
			} catch (ClmException e) {
				throw e; 
			} catch (Exception e1) {
				log.error("用户规则保存异常！",e1);
				throw new ClmException(ClmErrorEnum.CLM1809311.getErrorCode(),ClmErrorEnum.CLM1809311.getErrorMsg());
			}finally {
				//释放锁
				distributedLockHandler.releaseLock(lock);
			}
		}else {
			log.warn("用户规则保存未能获取资源锁！！！");
			throw new ClmException(ClmErrorEnum.CLM1809312.getErrorCode(),ClmErrorEnum.CLM1809312.getErrorMsg());
		}
	}
	
	/**
	 * 查询用户启用的规则路由列表
	 * @param sipShareId
	 * @return
	 */
	public List<SipRouteRule> queryBySipRouteListByUserId(String userId) {
		if(StrUtils.isNotEmpty(userId)) {
			SipRouteRuleExample example = new SipRouteRuleExample();
			example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(SipRouteRuleStatusEnum.OK.getCode());
			return sipRouteRuleMapper.selectByExample(example);
		}
		return null;
	}
	
	/**
	 * 根据用户查询sip路由规则
	 * @param userId
	 * @return
	 */
	public SipRouteRuleVO queryUserSipRouteRuleVO(String userId){
		if(StrUtils.isNotEmpty(userId)) {
			SipRouteRuleVO vo = new SipRouteRuleVO();
			vo.setUserId(userId);
			List<SipRouteRule> list = this.queryBySipRouteListByUserId(userId);
			//查询全部规则项
			List<SipRouteItem> sipRouteItemList = sipRouteItemService.queryAllSipRouteItemList();
			if(list!=null&&!list.isEmpty()) {
				SipRouteRule rule = list.get(0);	//现在1个用户只能配置1个规则
				vo.setId(rule.getId());
				vo.setRuleContent(rule.getRuleContent());
				String ruleContent = rule.getRuleContent();	//规则内容
				if(SipRouteRuleTypeEnum.PRI.getCode() == rule.getRuleType()) {
					//按优先级（规则内容中是：规则1,规则2 ，顺序配置）
					String[] ruleCtxArray = ruleContent.split(",");
					//按规则项顺序排序
					sipRouteItemList = this.sortSipRoutItem(sipRouteItemList, ruleCtxArray);
					if(sipRouteItemList!=null && !sipRouteItemList.isEmpty()) {
						List<SipRouteItemVO> itemList = new ArrayList<SipRouteItemVO>();
						for(SipRouteItem item:sipRouteItemList) {
							SipRouteItemVO itemVo = new SipRouteItemVO();
							itemVo.setItemCode(item.getItemCode());
							itemVo.setItemName(item.getItemName());
							if(ArrayUtil.contains(ruleCtxArray, item.getItemCode())) {
								itemVo.setSelected(true); //已选了
							}else {
								itemVo.setSelected(false);
							}
							itemList.add(itemVo);
						}
						vo.setItemList(itemList);
					}
				}
				return vo;
			}else {
				log.info("用户{}没有配置过规则",userId);
				if(sipRouteItemList!=null&&!sipRouteItemList.isEmpty()) {
					List<SipRouteItemVO> itemList = new ArrayList<SipRouteItemVO>();
					for(SipRouteItem item:sipRouteItemList) {
						SipRouteItemVO itemVo = new SipRouteItemVO();
						itemVo.setItemCode(item.getItemCode());
						itemVo.setItemName(item.getItemName());
						itemVo.setSelected(false);
						itemList.add(itemVo);
					}
					vo.setItemList(itemList);
				}
				return vo;
			}
		}
		return null;
	}
	
	
	/**
	 * 规则项跟保存的路由规则重新排序
	 * @return
	 */
	private List<SipRouteItem> sortSipRoutItem(List<SipRouteItem> itemList,String[] ruleCtxArray){
		List<SipRouteItem> sortedRoutItemList=new ArrayList<SipRouteItem>();
		if(ruleCtxArray!=null && itemList!=null) {
			for(String itemCode:ruleCtxArray) {
				Iterator<SipRouteItem> it = itemList.iterator();
				while(it.hasNext()){
					SipRouteItem item = it.next();
					//按规则原顺序排序
				    if(itemCode.equals(item.getItemCode())) {
						sortedRoutItemList.add(item);
						it.remove();
					}
				}
			}
			if(itemList!=null && !itemList.isEmpty()) {
				//没匹配的排到最后
				sortedRoutItemList.addAll(itemList);
			}
		}
		return sortedRoutItemList;
	}
	
	
	/**
	 * 根据主键查询规则路由
	 * @param id
	 * @return
	 */
	public SipRouteRule queryById(Integer id) {
		if(id!=null) {
			return sipRouteRuleMapper.selectByPrimaryKey(id);
		}
		return null;
	}
	
	
}
