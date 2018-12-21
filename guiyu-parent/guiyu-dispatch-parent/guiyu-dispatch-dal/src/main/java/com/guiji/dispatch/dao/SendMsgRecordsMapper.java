package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.SendMsgRecords;
import com.guiji.dispatch.dao.entity.SendMsgRecordsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SendMsgRecordsMapper {
    int countByExample(SendMsgRecordsExample example);

    int deleteByExample(SendMsgRecordsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SendMsgRecords record);

    int insertSelective(SendMsgRecords record);

    List<SendMsgRecords> selectByExample(SendMsgRecordsExample example);

    SendMsgRecords selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SendMsgRecords record, @Param("example") SendMsgRecordsExample example);

    int updateByExample(@Param("record") SendMsgRecords record, @Param("example") SendMsgRecordsExample example);

    int updateByPrimaryKeySelective(SendMsgRecords record);

    int updateByPrimaryKey(SendMsgRecords record);
}