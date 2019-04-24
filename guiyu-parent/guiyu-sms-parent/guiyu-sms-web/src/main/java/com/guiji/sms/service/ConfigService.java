package com.guiji.sms.service;

import com.guiji.sms.controller.bean.AuthLevelData;
import com.guiji.sms.controller.bean.Condition;
import com.guiji.sms.controller.bean.ConfListRsp;
import com.guiji.sms.controller.bean.ConfReq;
import com.guiji.sms.dao.entity.SmsConfig;

public interface ConfigService
{
	/**
	 * 获取短信配置
	 */
	SmsConfig getSendConfig(String templateId, String intentionTag, String orgCode);

	/**
	 * 获取短信配置列表
	 */
	ConfListRsp queryConfigList(Condition condition, AuthLevelData authLevelData);

	/**
	 * 新增配置
	 */
	void addConfig(ConfReq confReq, Long userId, String orgCode);

	/**
	 * 删除短信配置
	 */
	void delConfig(Integer id);

	/**
	 * 编辑短信配置
	 */
	void updateConfig(ConfReq confReq, Long userId);

	/**
	 * 短信配置一键停止
	 */
	void stopConfig(Integer id, Long userId);
	
	/**
	 * 短信配置一键启动
	 */
	void startConfig(Integer id, Long userId);

	/**
	 * 短信配置审核
	 */
	void auditingConfig(Integer id, Long userId);

}
