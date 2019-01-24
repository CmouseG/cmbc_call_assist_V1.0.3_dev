package com.guiji.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.service.RecordService;
import com.guiji.sms.dao.SmsRecordMapper;
import com.guiji.sms.dao.entity.SmsRecord;

@Service
public class RecordServiceImpl implements RecordService
{
	@Autowired
	SmsRecordMapper recordMapper;

	@Override
	public void saveRecord(List<SmsRecord> records, String platform)
	{
		for(SmsRecord record : records)
		{
			record.setPlatform(platform);
			recordMapper.insertSelective(record);
		}
	}

	@Override
	public void saveRecord(SmsRecord record, String platform)
	{
		record.setPlatform(platform);
		recordMapper.insertSelective(record);
	}
	
}
