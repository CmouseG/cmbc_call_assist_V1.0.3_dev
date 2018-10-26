package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutDetailRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CallOutDetailRecordMapper {
    int countByExample(CallOutDetailRecordExample example);

    int deleteByExample(CallOutDetailRecordExample example);

    int deleteByPrimaryKey(Integer callDetailId);

    int insert(CallOutDetailRecord record);

    int insertSelective(CallOutDetailRecord record);

    List<CallOutDetailRecord> selectByExample(CallOutDetailRecordExample example);

    CallOutDetailRecord selectByPrimaryKey(Integer callDetailId);

    int updateByExampleSelective(@Param("record") CallOutDetailRecord record, @Param("example") CallOutDetailRecordExample example);

    int updateByExample(@Param("record") CallOutDetailRecord record, @Param("example") CallOutDetailRecordExample example);

    int updateByPrimaryKeySelective(CallOutDetailRecord record);

    int updateByPrimaryKey(CallOutDetailRecord record);
}