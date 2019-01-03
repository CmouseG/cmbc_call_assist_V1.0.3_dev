package com.guiji.ai.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TtsResultMapperExt {
	
    List<Map<String, String>> getTtsTransferResult(String busId);
	
	//查询当前时间之前10分钟的GPU分配情况
    List<Map<String, Object>> selectTenMinutesBefore(Date now);
}