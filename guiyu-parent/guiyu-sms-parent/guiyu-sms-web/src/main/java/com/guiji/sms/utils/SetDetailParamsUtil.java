package com.guiji.sms.utils;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.guiji.sms.dao.entity.SmsSendDetail;

/**
 * 设置结果（发送详情）参数
 * 每个平台类中使用
 * @author Sun
 */
public class SetDetailParamsUtil
{
	// 设置结果参数
	public static void setParams(SmsSendDetail record, JSONObject params)
	{
		record.setOrgCode(params.getString("orgCode"));
		record.setTunnelName(params.getString("tunnelName"));
		record.setTaskName(params.getString("taskName"));
		record.setSendTime(new Date());
		record.setCreateId(params.getLong("createId").intValue());
		record.setCreateTime(params.getDate("createTime"));
		record.setSmsContent(params.getString("smsContent"));
	}
}
