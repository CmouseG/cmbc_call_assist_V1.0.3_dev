package com.guiji.sms.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.ConfListRsp;
import com.guiji.sms.controller.bean.ConfReq;
import com.guiji.sms.dao.SmsConfigMapper;
import com.guiji.sms.dao.entity.SmsConfig;
import com.guiji.sms.dao.entity.SmsConfigExample;
import com.guiji.sms.service.ConfigService;
import com.guiji.sms.utils.AuthUtil;
import com.guiji.utils.RedisUtil;

@Service
public class ConfigServiceImpl implements ConfigService
{
	@Autowired
	SmsConfigMapper configMapper;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	AuthUtil authUtil;
	
	/**
	 * 获取短信配置（发送）
	 */
	@Override
	public SmsConfig getSendConfig(String templateId, String intentionTag, String orgCode)
	{
		SmsConfigExample example = new SmsConfigExample();
		example.createCriteria().andTemplateIdEqualTo(templateId)
								.andIntentionTagLike("%"+intentionTag+"%")
								.andOrgCodeLike(orgCode+"%")
								.andAuditingStatusEqualTo(1) // 已审核
								.andRunStatusEqualTo(1); // 已启动
		List<SmsConfig> configs = configMapper.selectByExampleWithBLOBs(example);
		if(CollectionUtils.isEmpty(configs)){return null;}
		return configs.get(0);
	}

	/**
	 * 获取短信配置列表
	 */
	@Override
	public ConfListRsp queryConfigList(Condition condition, AuthLevelData authLevelData)
	{
		ConfListRsp rsp = new ConfListRsp();
		SmsConfigExample example = new SmsConfigExample();
		Integer authLevel = authLevelData.getAuthLevel();
		if(authLevel == 1) {
			example.createCriteria().andCreateIdEqualTo(authLevelData.getUserId().intValue());
		} else if(authLevel == 2) {
			example.createCriteria().andOrgCodeEqualTo(authLevelData.getOrgCode());
		}else if(authLevel == 3) {
			example.createCriteria().andOrgCodeLike(authLevelData.getOrgCode() + "%");
		}
		rsp.setTotalNum(configMapper.countByExample(example));
		example.setLimitStart((condition.getPageNum()-1)*condition.getPageSize());
		example.setLimitEnd(condition.getPageSize());
		example.setOrderByClause("id desc");
		rsp.setRecords(configMapper.selectByExampleWithBLOBs(example));
		return rsp;
	}

	/**
	 * 新增配置
	 */
	@Override
	@Transactional
	public void addConfig(ConfReq confReq, Long userId, String orgCode)
	{
		SmsConfig config = new SmsConfig();
		config.setTunnelName(confReq.getTunnelName());
		config.setTemplateId(confReq.getTemplateId());
		config.setTemplateName(confReq.getTemplateName());
		config.setIntentionTag(confReq.getIntentionTag());
		config.setContentType(confReq.getContentType());
		config.setSmsContent(confReq.getSmsContent());
		config.setOrgCode(orgCode);
		config.setOrgName(authUtil.getOrgNameByOrgCode(orgCode));
		config.setCreateId(userId.intValue());
		config.setCreateName(authUtil.getUserNameByUserId(userId));
		config.setCreateTime(new Date());
		configMapper.insertSelective(config);
	}

	/**
	 * 删除短信配置
	 */
	@Override
	@Transactional
	public void delConfig(Integer id)
	{
		SmsConfig config = configMapper.selectByPrimaryKey(id);
		deleteRedisCache(config); // 删除redis缓存
		configMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 编辑短信配置
	 */
	@Override
	@Transactional
	public void updateConfig(ConfReq confReq, Long userId)
	{
		SmsConfig config = configMapper.selectByPrimaryKey(confReq.getId());
		deleteRedisCache(config); // 删除redis缓存
		config.setTunnelName(confReq.getTunnelName());
		config.setTemplateId(confReq.getTemplateId());
		config.setTemplateName(confReq.getTemplateName());
		config.setIntentionTag(confReq.getIntentionTag());
		config.setContentType(confReq.getContentType());
		config.setSmsContent(confReq.getSmsContent());
		config.setAuditingStatus(0); // 编辑后需要重新审核
		config.setUpdateId(userId.intValue());
		config.setUpdateName(authUtil.getUserNameByUserId(userId));
		config.setUpdateTime(new Date());
		configMapper.updateByPrimaryKeySelective(config);
	}

	/**
	 * 短信配置一键停止
	 */
	@Override
	@Transactional
	public void stopConfig(Integer id, Long userId)
	{
		SmsConfig config = configMapper.selectByPrimaryKey(id);
		deleteRedisCache(config); // 删除redis缓存
		config.setRunStatus(0);
		config.setUpdateId(userId.intValue());
		config.setUpdateName(authUtil.getUserNameByUserId(userId));
		config.setUpdateTime(new Date());
		configMapper.updateByPrimaryKeySelective(config);
	}
	
	/**
	 * 短信配置一键启动
	 */
	@Override
	@Transactional
	public void startConfig(Integer id, Long userId)
	{
		SmsConfig config = configMapper.selectByPrimaryKey(id);
		deleteRedisCache(config); // 删除redis缓存
		config.setRunStatus(1);
		config.setUpdateId(userId.intValue());
		config.setUpdateName(authUtil.getUserNameByUserId(userId));
		config.setUpdateTime(new Date());
		configMapper.updateByPrimaryKeySelective(config);
	}

	/**
	 * 短信配置审核
	 */
	@Override
	public void auditingConfig(Integer id, Long userId)
	{
		SmsConfig config = configMapper.selectByPrimaryKey(id);
		deleteRedisCache(config); // 删除redis缓存
		config.setAuditingStatus(1);
		config.setUpdateId(userId.intValue());
		config.setUpdateName(authUtil.getUserNameByUserId(userId));
		config.setUpdateTime(new Date());
		configMapper.updateByPrimaryKeySelective(config);
	}
	
	// 删除redis缓存
	private void deleteRedisCache(SmsConfig config)
	{
		String orgCode = config.getOrgCode();
		String templateId = config.getTemplateId();
		String intentionTag = config.getIntentionTag();
		String[] singleIntentionTags = intentionTag.split(" ");
		for(String singleIntentionTag : singleIntentionTags){
			redisUtil.del(orgCode+"_"+templateId+"_"+singleIntentionTag);
		}
	}

}
