package com.guiji.ai.dao;

import com.guiji.ai.dao.entity.TtsStatus;
import com.guiji.ai.dao.entity.TtsStatusExample;
import java.util.List;
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
}