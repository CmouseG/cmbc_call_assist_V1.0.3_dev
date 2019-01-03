package com.guiji.ai.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TtsStatusMapperExt {
    
	int getReqStatusByBusId(String busId);
	
	int updateStatusByBusId(@Param("busId") String busId, @Param("status") int status);

	int updateJumpFlagByBusId(String busId);

	//待合成任务数（分模型）
	List<Map<String, Object>> getWaitTasks();

    //按天统计 接受任务数和完成任务数
    List<Map<String, Object>> getTasksByDays(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    //按月统计 接受任务数和完成任务数
    List<Map<String, Object>> getTasksByMonths(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}