package com.guiji.ai.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.guiji.ai.dao.entity.TtsStatus;

public interface TtsStatusMapper
{

	String getReqStatusByBusId(String busId);

	List<Map<String, Object>> getTtsStatus(Date startTime, Date endTime, String model, String status);

	int insert(TtsStatus record);

	int updateStatusByBusId(String busId, String status);
	
}
