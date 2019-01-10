package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.PushRecords;
import com.guiji.dispatch.dao.entity.PushRecordsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PushRecordsMapper {
    int countByExample(PushRecordsExample example);

    int deleteByExample(PushRecordsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PushRecords record);

    int insertSelective(PushRecords record);

    List<PushRecords> selectByExample(PushRecordsExample example);

    PushRecords selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PushRecords record, @Param("example") PushRecordsExample example);

    int updateByExample(@Param("record") PushRecords record, @Param("example") PushRecordsExample example);

    int updateByPrimaryKeySelective(PushRecords record);

    int updateByPrimaryKey(PushRecords record);
}