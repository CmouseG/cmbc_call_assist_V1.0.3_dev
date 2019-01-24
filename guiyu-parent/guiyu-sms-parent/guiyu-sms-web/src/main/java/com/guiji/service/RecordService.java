package com.guiji.service;

import java.util.List;

import com.guiji.sms.dao.entity.SmsRecord;

public interface RecordService
{
	/*
	 * 保存记录
	 */
	void saveRecord(List<SmsRecord> records, String platform);
	
	/*
	 * 保存记录
	 */
	void saveRecord(SmsRecord record, String platform);
}