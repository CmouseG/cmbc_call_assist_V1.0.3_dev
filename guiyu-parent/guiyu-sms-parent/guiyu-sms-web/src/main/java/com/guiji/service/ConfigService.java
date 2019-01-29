package com.guiji.service;

import com.guiji.sms.dao.entity.SmsConfig;
import com.guiji.sms.vo.ConfigListReqVO;
import com.guiji.sms.vo.ConfigListRspVO;
import com.guiji.sms.vo.ConfigReqVO;

public interface ConfigService
{
	/**
	 * 获取短信配置列表
	 */
	ConfigListRspVO getConfigList(ConfigListReqVO configListReq, Long userId);

	/**
	 * 新增短信配置
	 * @param userId 
	 */
	void addConfig(ConfigReqVO configReq, Long userId);

	/**
	 * 删除短信配置
	 */
	void delConfig(Integer id);

	/**
	 * 编辑短信配置
	 * @param userId 
	 */
	void updateConfig(ConfigReqVO configReq, Long userId);

	/**
	 * 短信配置一键停止
	 */
	void stopConfig(Integer id);

	/**
	 * 短信配置审核
	 */
	void auditingConfig(Integer id);

	/**
	 * 根据意向标签和用户获取配置
	 */
	SmsConfig getConfigToSend(String intentionTag, String orgCode, String templateId);

}
