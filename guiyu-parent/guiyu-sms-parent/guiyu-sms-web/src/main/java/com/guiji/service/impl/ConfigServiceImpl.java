package com.guiji.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.service.ConfigService;
import com.guiji.sms.dao.SmsConfigMapper;
import com.guiji.sms.dao.SmsConfigMapperExt;
import com.guiji.sms.dao.entity.SmsConfig;
import com.guiji.sms.dao.entity.SmsConfigExample;
import com.guiji.sms.vo.ConfigListReqVO;
import com.guiji.sms.vo.ConfigListRspVO;
import com.guiji.sms.vo.ConfigReqVO;
import com.guiji.user.dao.entity.SysOrganization;

@Service
public class ConfigServiceImpl implements ConfigService
{
	@Autowired
	IAuth iAuth;
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

		SmsConfigExample example = new SmsConfigExample();
		configListRsp.setTotalCount(configMapper.selectByExampleWithBLOBs(example).size()); // 总条数

		example.setLimitStart((configListReq.getPageNum() - 1) * configListReq.getPageSize());
		example.setLimitEnd(configListReq.getPageSize());
		configListRsp.setSmsConfigList(configMapper.selectByExampleWithBLOBs(example)); // 分页返回的记录

		return configListRsp;
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
		ReturnData<SysOrganization> sysOrganization = iAuth.getOrgByUserId(userId);
		smsConfig.setCompanyId(sysOrganization.body.getId().intValue());
		smsConfig.setCompanyName(sysOrganization.body.getName());
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
}
