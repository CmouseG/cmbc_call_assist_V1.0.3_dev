package com.guiji.sms.dao;

import org.apache.ibatis.annotations.Param;

import com.guiji.sms.dao.entity.SmsConfig;

public interface SmsConfigMapperExt 
{
	/**
	 * 一键停止（修改运行状态）
	 */
    int updateRunStatusByPrimaryKey(Integer id);

    /**
	 * 审核（修改审核状态和运行状态）
	 */
	int updateAuditingStatusAndRunStatusByPrimaryKey(Integer id);

	/**
	 * 根据意向标签和用户获取配置
	 */
	SmsConfig getConfigByIntentionTagAndOrgCode(@Param("intentionTag") String intentionTag, @Param("orgCode") String orgCode);
}