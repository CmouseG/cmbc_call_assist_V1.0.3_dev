package com.guiji.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.service.ConfigService;
import com.guiji.sms.dao.SmsConfigMapper;
import com.guiji.sms.dao.SmsConfigMapperExt;
import com.guiji.sms.dao.entity.SmsConfig;
import com.guiji.sms.dao.entity.SmsConfigExample;
import com.guiji.sms.vo.ConfigListReqVO;
import com.guiji.sms.vo.ConfigListRspVO;
import com.guiji.sms.vo.ConfigReqVO;
import com.guiji.sms.vo.SmsConfigVO;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.RedisUtil;

@Service
public class ConfigServiceImpl implements ConfigService
{
	private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);
	
	@Autowired
	IAuth auth;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	SmsConfigMapper configMapper;
	@Autowired
	SmsConfigMapperExt configMapperExt;

	/**
	 * 获取短信配置列表
	 */
	@Override
	public ConfigListRspVO getConfigList(ConfigListReqVO configListReq)
	{
		ConfigListRspVO configListRsp = new ConfigListRspVO();
		List<SmsConfigVO> configVOList = new ArrayList<>();

		SmsConfigExample example = new SmsConfigExample();
		configListRsp.setTotalCount(configMapper.selectByExampleWithBLOBs(example).size()); // 总条数

		example.setLimitStart((configListReq.getPageNum() - 1) * configListReq.getPageSize());
		example.setLimitEnd(configListReq.getPageSize());
		List<SmsConfig> configList = configMapper.selectByExampleWithBLOBs(example); // 分页返回的记录
		for(SmsConfig config : configList)
		{
			SmsConfigVO configVO = setVoPrrams(config);
			configVOList.add(configVO);
		}
		configListRsp.setSmsConfigVOList(configVOList);

		return configListRsp;
	}

	private SmsConfigVO setVoPrrams(SmsConfig config)
	{
		SmsConfigVO configVO = new SmsConfigVO();
		configVO.setId(config.getId());
		configVO.setTunnelName(config.getTunnelName());
		configVO.setTemplateName(config.getTemplateName());
		configVO.setIntentionTag(config.getIntentionTag());
		configVO.setSmsTemplateId(config.getSmsTemplateId());
		configVO.setSmsContent(config.getSmsContent());
		configVO.setAuditingStatus(config.getAuditingStatus());
		configVO.setRunStatus(config.getRunStatus());
		configVO.setCompanyName(config.getCompanyName());
		configVO.setCreateUser(getUserName(config.getCreateId().toString()));
		configVO.setCreateTime(config.getCreateTime());
		return configVO;
	}

	/**
	 * 新增短信配置
	 */
	@Override
	public void addConfig(ConfigReqVO configReq, Long userId)
	{
		SmsConfig smsConfig = setParams(configReq, userId);
		configMapper.insertSelective(smsConfig);
	}
	

	/**
	 * 设置字段值
	 */
	private SmsConfig setParams(ConfigReqVO configReq, Long userId)
	{
		SmsConfig smsConfig = new SmsConfig();
		if(configReq.getId() != null) // 新增id为空，修改id不为空
		{
			smsConfig.setId(configReq.getId());
		}
		smsConfig.setTunnelName(configReq.getTunnelName());
		smsConfig.setTemplateId(configReq.getTemplateId());
		smsConfig.setTemplateName(configReq.getTemplateName());
		smsConfig.setIntentionTag(configReq.getIntentionTag());
		smsConfig.setSmsContent(configReq.getSmsContent());
		smsConfig.setSmsTemplateId(configReq.getSmsTemplateId());
		if(configReq.getSmsTemplateId() != null) // 短信模版
		{
			smsConfig.setRunStatus(1); // 运行状态：0-停止；1-启动
			smsConfig.setAuditingStatus(1); // 审核状态：0-待审核；1-已审核
		}
		else // 自定义短信内容 
		{
			smsConfig.setRunStatus(0); // 运行状态：0-停止；1-启动
			smsConfig.setAuditingStatus(0); // 审核状态：0-待审核；1-已审核
		}
		ReturnData<SysOrganization> sysOrganization = auth.getOrgByUserId(userId);
		smsConfig.setCompanyId(sysOrganization.body.getId().intValue());
		smsConfig.setCompanyName(sysOrganization.body.getName());
		smsConfig.setOrgCode(sysOrganization.body.getCode());
		smsConfig.setCreateId(userId.intValue());
		smsConfig.setCreateTime(new Date());
		smsConfig.setUpdateId(userId.intValue());
		smsConfig.setUpdateTime(new Date());
		return smsConfig;
	}

	/**
	 * 删除短信配置
	 */
	@Override
	public void delConfig(Integer id)
	{
		configMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 编辑短信配置
	 */
	@Override
	public void updateConfig(ConfigReqVO configReq, Long userId)
	{
		SmsConfig smsConfig = setParams(configReq, userId);
		configMapper.updateByPrimaryKeySelective(smsConfig);
	}

	/**
	 * 短信配置一键停止
	 */
	@Override
	public void stopConfig(Integer id)
	{
		configMapperExt.updateRunStatusByPrimaryKey(id);
	}

	/**
	 * 短信配置审核
	 */
	@Override
	public void auditingConfig(Integer id)
	{
		configMapperExt.updateAuditingStatusAndRunStatusByPrimaryKey(id);
	}

	/**
	 * 根据意向标签和用户获取配置
	 */
	@Override
	public SmsConfig getConfigByIntentionTagAndOrgCode(String intentionTag, String orgCode)
	{
		return configMapperExt.getConfigByIntentionTagAndOrgCode(intentionTag, orgCode);
	}
	
	public String getUserName(String userId) {
		String cacheName = (String) redisUtil.get(userId);
		if (cacheName != null) {
			return cacheName;
		} else {
			try {
				Result.ReturnData<SysUser> result = auth.getUserById(Long.valueOf(userId));
				if(result!=null && result.getBody()!=null) {
					String userName = result.getBody().getUsername();
					if (userName != null) {
						redisUtil.set(userId, userName);
						return userName;
					}
				}
			} catch (Exception e) {
				logger.error(" auth.getUserName error :" + e);
			}
		}
		return "";
	}
}
