package com.guiji.ai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guiji.ai.dao.entity.TtsStatus;
import com.guiji.ai.dao.entity.TtsStatusExample;

public interface TtsStatusMapper {
    int countByExample(TtsStatusExample example);

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
	
	int updateStatusByBusId(String busId, String status);

}