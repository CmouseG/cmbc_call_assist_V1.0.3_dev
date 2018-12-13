package com.guiji.ai.dao;

import com.guiji.ai.dao.entity.TtsStatus;
import com.guiji.ai.dao.entity.TtsStatusExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TtsStatusMapper {
    long countByExample(TtsStatusExample example);

    int deleteByExample(TtsStatusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TtsStatus record);

    int insertSelective(TtsStatus record);

    List<TtsStatus> selectByExample(TtsStatusExample example);

    TtsStatus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TtsStatus record, @Param("example") TtsStatusExample example);

    int updateByExample(@Param("record") TtsStatus record, @Param("example") TtsStatusExample example);

    int updateByPrimaryKeySelective(TtsStatus record);

    int updateByPrimaryKey(TtsStatus record);
    
    String getReqStatusByBusId(String busId);
	
	int updateStatusByBusId(@Param("busId") String busId, @Param("status") String status);

	int updateJumpFlagByBusId(String busId);

	//按天统计 接受任务数
	List<Map<String, Object>> getAcceptTasksByDays(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

	//按月统计 接受任务数
	List<Map<String, Object>> getAcceptTasksByMonths(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	//按天统计 完成任务数
	List<Map<String, Object>> getCompleteTasksByDays(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	//按月统计 完成任务数
	List<Map<String, Object>> getCompleteTasksByMonths(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

	//待合成任务数（分模型）
	List<Map<String, Object>> getWaitTasks();

}